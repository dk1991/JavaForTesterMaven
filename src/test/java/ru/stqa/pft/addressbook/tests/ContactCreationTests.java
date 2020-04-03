package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/valid_contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> invalidContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/invalid_contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @BeforeClass
    public void getAvailableGroup() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test666").withHeader("test777").withFooter("test888"));
        }
    }

    @Test(dataProvider = "validContactsFromJson", priority = 1)
    public void testContactCreation(ContactData contact) {
        Groups groups = app.db().groups();
        File photo = new File("src/test/resources/bliznecy.png");
        contact.withPhoto(photo).inGroup(groups.iterator().next());
        app.goTo().homePage();
        //Contacts before = app.contact().all();
        Contacts before = app.db().contacts();
        app.contact().create(contact, true);
        assertThat(/*app.contact().count()*/app.db().contacts().size(), equalTo(before.size() + 1));
        //Contacts after = app.contact().all();
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt(c -> c.getId()).max().getAsInt()))));
    }

    @Test(dataProvider = "invalidContactsFromJson", priority = 2)
    public void testBadContactCreation(ContactData contact) {
        Groups groups = app.db().groups();
        File photo = new File("src/test/resources/bliznecy.png");
        contact.withPhoto(photo).inGroup(groups.iterator().next());
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        app.contact().create(contact, true);
        assertThat(app.db().contacts().size(), equalTo(before.size()));
        Contacts after = app.db().contacts();
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
