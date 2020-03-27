package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Jack").withLastName("Black"),true);
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.contact().all();

        app.contact().selectContactById(before.size() - 1);
        app.contact().deleteSelectedContact();
        app.contact().returnToHomePage();

        Contacts after = app.contact().all();
        Assert.assertEquals(after.size(),before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
