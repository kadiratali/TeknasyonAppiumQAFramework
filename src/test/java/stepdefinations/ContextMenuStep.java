package stepdefinations;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.ContextMenuPage;

public class ContextMenuStep {

    ContextMenuPage contextMenuPage = new ContextMenuPage();

    @When("the user long presses the {string} button")
    public void theUserLongPressesTheButton(String longPressButton) {
        contextMenuPage.theUserLongPressesTheButton(longPressButton);
    }

    @Then("{string} and {string} are displayed")
    public void andAreDisplayed(String menuA, String menuB) {
        contextMenuPage.verifyMenuAAndBVisible(menuA, menuB);
    }

}
