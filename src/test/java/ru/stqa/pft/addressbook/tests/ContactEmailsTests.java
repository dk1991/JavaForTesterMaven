package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailsTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstName("Rita").withLastName("Bubnova")/*.withGroup("test1")*/
                    .withEmail("gog@yandex.com").withEmail2("bias@mail.ru").withEmail3("doggy@gmail.new"),true);
        }
    }

    @Test
    public void testContactEmails() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((e) -> ! e.equals("")) // проверить, чтобы строка не была пустой
                .map(ContactEmailsTests::cleaned) // применить метод cleaned() к каждому элементу
                .collect(Collectors.joining("\n")); // соединить все элементы в строку с переносом строки
    }

    private static String cleaned(String email) {
        return email.replaceAll("\\s","");
    }
}

