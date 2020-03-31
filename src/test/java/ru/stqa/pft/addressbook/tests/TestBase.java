package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

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

    @BeforeMethod
    public void logTestStart(Method method, Object[] parameters) {
        logger.info("Start test {} with parameters {}", method.getName(), Arrays.asList(parameters));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method method) {
        logger.info("Stop test {}", method.getName());
    }
}
