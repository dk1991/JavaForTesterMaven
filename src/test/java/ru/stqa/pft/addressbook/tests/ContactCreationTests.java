package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    private String groupName = "";

    @BeforeClass
    public void getAvailableGroup() {
        app.goTo().groupPage();
        if (app.group().count() > 0) {
            groupName = app.group().firstGroup().getName();
        } else {
            app.group().create(new GroupData().withName("test666").withHeader("test777").withFooter("test888"));
            groupName = "test666";
        }
    }

    @Test
    public void testContactCreation() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        File photo = new File("src/test/resources/bliznecy.png");
        ContactData contact = new ContactData().withFirstName("Eva").withLastName("Kanaeva")
                .withGroup(groupName).withPhoto(photo);
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
    }

    @Test
    public void testBadContactCreation() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Vova'").withLastName("Kanaeva").withGroup(groupName);
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }

    /*@Test
    public void currentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/bliznecy.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }*/
}
