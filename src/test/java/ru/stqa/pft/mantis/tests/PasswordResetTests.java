package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class PasswordResetTests extends TestBase {

    @BeforeMethod // для почты Wiser
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testPasswordReset() throws IOException, MessagingException {
        app.admin().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        app.goTo().manageUsersPage();
        UserData user = app.db().users().get(1); // 0 - это админ
        app.admin().resetUserPassword(user);
        //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        //String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true) // для почты Wiser
    public void stopMailServer() {
        app.mail().stop();
    }
}
