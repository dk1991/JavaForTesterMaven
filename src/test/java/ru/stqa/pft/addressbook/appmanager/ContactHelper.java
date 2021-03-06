package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.xpath("//a[text()='add new']"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.xpath("//input[@name='home']"), contactData.getHomePhone());
        type(By.xpath("//input[@name='mobile']"), contactData.getMobilePhone());
        type(By.xpath("//input[@name='work']"), contactData.getWorkPhone());
        type(By.xpath("//textarea[@name='address']"), contactData.getAddress());
        type(By.xpath("//input[@name='email']"), contactData.getEmail());
        type(By.xpath("//input[@name='email2']"), contactData.getEmail2());
        type(By.xpath("//input[@name='email3']"), contactData.getEmail3());
        if (contactData.getPhoto() != null ) {
            attach(By.name("photo"), contactData.getPhoto());
        }

        if (creation) {
//            List<WebElement> availableGroups = wd.findElements(By.xpath("//select[@name='new_group']/option"));
//            for (WebElement group : availableGroups) {
//                if (group.getText().equals(contactData.getGroup())) {
//                    new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
//                    return;
//                }
//            }
            if (contactData.getGroups().size() > 0) {
                assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group")))
                        .selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void returnToHomePage() {
        click(By.xpath("//a[text()='home']"));
    }

    public void initContactModificationById(int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();

        // или так:
//        wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
//        wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[8]/a", id))).click();
//        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void goToContactDetailsById(int id) {
        wd.findElement(By.xpath(String.format("//tr[.//input[@value='%s']]/td[7]/a", id))).click();
    }

    public void submitContactModification() {
        click(By.xpath("//input[@value='Update']"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id='" + id + "'")).click();
    }

    public void selectContactByLastName(String lastName) {
        wd.findElement(By.xpath(String.format("//td[text()='%s']/..//input", lastName))).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void create(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        contactCache = null;
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void addToGroup(ContactData contact, GroupData group) {
        selectContactByLastName(contact.getLastName());
        new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
        click(By.xpath("//input[@value='Add to']"));
        returnToHomePage();
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();

        return new ContactData().withFirstName(firstName).withLastName(lastName).withId(contact.getId())
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withAddress(address)
                .withEmail(email).withEmail2(email2).withEmail3(email3);
    }

    public ContactData infoFromDetailsForm(ContactData contact) {
        goToContactDetailsById(contact.getId());
        String mainInfo = wd.findElement(By.xpath("//div[@id='content']")).getText();
        wd.navigate().back();

        return new ContactData().withMainInfo(mainInfo).withId(contact.getId());
    }

    public boolean isThereAnyContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        contactCache = new Contacts();
        List<WebElement> rows = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String firstName = cells.get(2).getText();
            String lastName = cells.get(1).getText();
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
            String allPhones = cells.get(5).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();

            contactCache.add(new ContactData().withFirstName(firstName).withLastName(lastName).withId(id)
                    .withAllPhones(allPhones).withAddress(address).withAllEmails(allEmails));
        }
        return new Contacts(contactCache);
    }
}
