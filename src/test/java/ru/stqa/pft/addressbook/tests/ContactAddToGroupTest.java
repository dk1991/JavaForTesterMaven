package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTest extends TestBase {

    private static ContactData contact = new ContactData();
    private static GroupData addedGroup = new GroupData();

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0 && app.db().contacts().size() != 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            addedGroup = app.db().groups().iterator().next();
            contact = app.db().contacts().iterator().next();
        } else if (app.db().contacts().size() == 0 && app.db().groups().size() != 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstName("Jack").withLastName("Black"),true);
            addedGroup = app.db().groups().iterator().next();
            contact = app.db().contacts().iterator().next();
        } else if (app.db().contacts().size() == 0 && app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstName("Jack").withLastName("Black"),true);
            addedGroup = app.db().groups().iterator().next();
            contact = app.db().contacts().iterator().next();
        } else {
            Contacts contacts = app.db().contacts();
            Groups groups = app.db().groups();
            //Set<String> groupsName = groups.stream().map((g) -> g.getName()).collect(Collectors.toSet());
            for (ContactData c : contacts) {
                Set<String> groupsNames = c.getGroups().stream().map((g) -> g.getName()).collect(Collectors.toSet());
                for (GroupData g : groups) {
                    if (! groupsNames.contains(g.getName())) {
                        contact = c;
                        addedGroup = g;
                        return;
                    }
                }
            }
            if (contact.getFirstName() == null) {
                app.goTo().groupPage();
                GroupData groupData = new GroupData().withName("test987654");
                app.group().create(groupData);
                contact = app.db().contacts().iterator().next();
                addedGroup = groupData;
            }
        }
    }

    @Test
    public void testContactAddToGroup() {
        app.goTo().homePage();
        int countGroupBefore = contact.getGroups().size();
        app.contact().addToGroup(contact, addedGroup);
        //int countGroupAfter = contact.getGroups().size(); необходимо проверить число групп после добавления
        //assertThat(countGroupAfter, equalTo(countGroupBefore + 1));
    }
}
