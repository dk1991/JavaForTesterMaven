package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Jack").withLastName("Black"),true);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact().all();
        app.contact().initContactModificationById(before.size() - 1);
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withFirstName("Bob").withLastName("Robbinson").withId(modifiedContact.getId());

        app.contact().fillContactForm(contact, false);
        app.contact().submitContactModification();
        app.contact().returnToHomePage();

        Contacts after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
