package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

    //@BeforeMethod // для почты Wiser
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException, MessagingException {
        long now = System.currentTimeMillis(); // текущее время от начала 1970 года
        String user = String.format("user%s", now);
        String password = "password";
        String email = String.format("user%s@localhost", now);
        //String email = String.format("user%s@localhost.localdomain", now); // для почты Wiser
        String name = "Dimas";
        app.james().createUser(user, password);
        app.registration().start(user, email);
        //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        List<MailMessage> mailMessages = app.james().waitForMail(user, password, 120000);
        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password, name);
        assertTrue(app.newSession().login(user, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    //@AfterMethod(alwaysRun = true) // для почты Wiser
    public void stopMailServer() {
        app.mail().stop();
    }
}
