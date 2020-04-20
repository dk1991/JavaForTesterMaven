package ru.stqa.pft.addressbook.bdd;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.remote.BrowserType;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.net.MalformedURLException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupStepDefenitions {

    private ApplicationManager app;
    private Groups groups;
    private GroupData newGroup;

    @Before
    public void init() throws MalformedURLException {
        app = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));
        app.init();
    }

    @After
    public void stop() {
        app.stop();
        app = null;
    }

    @Given("a set of groups")
    public void loadGroups() {
        groups = app.db().groups();
    }

    @When("I create a new group with name {string}, header {string}, footer {string}")
    public void createGroup(String name, String header, String footer) {
        app.goTo().groupPage();
        newGroup = new GroupData().withName(name).withHeader(header).withFooter(footer);
        app.group().create(newGroup);
    }

    @Then("the new set of groups is equal to the old set with the edit group")
    public void verifyGroupCreated() {
        Groups newGroups = app.db().groups();
        assertThat(newGroups, equalTo(
                groups.withAdded(newGroup.withId(newGroups.stream().mapToInt(g -> g.getId()).max().getAsInt()))));
    }
}
