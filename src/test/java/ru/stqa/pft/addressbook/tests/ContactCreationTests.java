package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstName("Eva").withLastName("Kanaeva").withGroup("test1");
        app.contact().create(contact, true);
        Contacts after = app.contact().all();
        Assert.assertEquals(after.size(),before.size() + 1);

        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
