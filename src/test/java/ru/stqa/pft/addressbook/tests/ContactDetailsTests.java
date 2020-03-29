package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailsTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("Mag").withLastName("Buben")
                    .withGroup("test5").withAddress("Moscow VDNKH Konstantinova 33-155")
                    .withHomePhone("+7(495)1450033").withMobilePhone("8(903)28288501").withWorkPhone("777-33-21")
                    .withEmail("spider@yandex.com").withEmail2("crocodile@mail.ru").withEmail3("cat@gmail.net"), true);
        }
    }

    @Test
    public void testContactDetails() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        ContactData contactInfoFromDetailsForm = app.contact().infoFromDetailsForm(contact);
        assertThat(cleanInfo(concatInfoFromEditForm(contactInfoFromEditForm)), equalTo(cleanInfo(contactInfoFromDetailsForm.getMainInfo())));
    }

    private String cleanInfo(String info) {
        if (info.contains("Member of:")) {
            int indexGroup = info.indexOf("Member of:");
            String infoWithoutGroup = info.substring(0, indexGroup);
            return infoWithoutGroup.replaceAll("\\s", "")
                    .replaceAll("[-()]", "").replaceAll("W:", "")
                    .replaceAll("M:", "").replaceAll("H:", "");
        } else {
            return info.replaceAll("\\s", "")
                    .replaceAll("[-()]", "").replaceAll("W:", "")
                    .replaceAll("M:", "").replaceAll("H:", "");
        }
    }

    private String concatInfoFromEditForm(ContactData contact) {
        return Arrays.asList(contact.getFirstName(), contact.getLastName(), contact.getAddress(),
                contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(), contact.getEmail(),
                contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining(""));
    }
}
