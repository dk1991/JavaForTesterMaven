package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Listeners(MyTestListener.class)
public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); // VM options: -ea -Dbrowser="firefox"

    @BeforeSuite
    public void setUp(ITestContext context) throws MalformedURLException {
        app.init();
        context.setAttribute("app", app);
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

    public void verifyGroupListInUI() {
//        if (Boolean.getBoolean("verifyUI")) { // чтобы вкл этот тест, необходимо передавать в VM options: -DverifyUI=true
            Groups dbGroups = app.db().groups();
            Groups uiGroups = app.group().all();
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet())));
        //}
    }
}
