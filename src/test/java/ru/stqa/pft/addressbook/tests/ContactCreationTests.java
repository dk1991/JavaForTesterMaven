package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().goToHomePage();
        int before = app.getContactHelper().getGroupCount();
        app.getContactHelper().createContact(new ContactData("Vera", "Kanaeva", "test1"), true);
        int after = app.getContactHelper().getGroupCount();
        Assert.assertEquals(after,before + 1);
    }
}
