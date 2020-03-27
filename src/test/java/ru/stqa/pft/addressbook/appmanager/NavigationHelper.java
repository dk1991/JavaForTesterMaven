package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.xpath("//input[@value='New group']"))) {
            return;
        } else {
            click(By.linkText("groups"));
        }
    }

    public void homePage() {
        if (! isElementPresent(By.id("maintable"))) {
            click(By.xpath("//a[text()='home']"));
        }
    }
}
