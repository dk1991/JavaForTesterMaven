package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

public class AdminHelper extends HelperBase {

    public AdminHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String name, String password) {
        type(By.name("username"), name);
        click(By.xpath("//input[@value='Login']"));
        type(By.name("password"), password);
        click(By.xpath("//span[text()='Only allow your session to be used from this IP address.']"));
        click(By.xpath("//input[@value='Login']"));
    }

    public void selectUserById(int id) {
        click(By.xpath(String.format("//a[@href='manage_user_edit_page.php?user_id=%s']", id)));
    }

    public void resetUserPassword(UserData user) {
        selectUserById(user.getId());
        click(By.xpath("//input[@value='Reset Password']"));
    }

    public void finishResetPassword(String confirmationLink, String password) {
        wd.get(confirmationLink);
    }
}
