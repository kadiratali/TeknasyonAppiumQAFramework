package stepdefinations;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ActionBarTabPage;

public class ActionBarTabStep {
    ActionBarTabPage actionBarTabPage = new ActionBarTabPage();


    @Then("the {string} option should be inactive")
    public void theOptionShouldBeInactive(String tabBar) {
        actionBarTabPage.theOptionShouldBeInactive(tabBar);
    }

    @When("the user adds {int} new tabs")
    public void theUserAddsNewTabs(int newTabCounter) {
        actionBarTabPage.theUserAddsNewTabs(newTabCounter);
    }
}
