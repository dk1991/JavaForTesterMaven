package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); // VM options: -ea -Dbrowser="firefox"

    @BeforeSuite
    public void setUp() {
        app.init();
    }

    @AfterSuite(alwaysRun = true) // alwaysRun - выполнять этот метод, даже если тест упадет
    public void tearDown() {
        app.stop();
    }
}
