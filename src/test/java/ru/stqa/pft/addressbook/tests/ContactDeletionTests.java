package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactHelper().getGroupCount();
        if (! app.getContactHelper().isThereAnyContact()) {
            app.getContactHelper().createContact(new ContactData("Jack","Daniels","test1"),true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getContactHelper().returnToHomePage();
        int after = app.getContactHelper().getGroupCount();
        Assert.assertEquals(after,before - 1);
    }
}
