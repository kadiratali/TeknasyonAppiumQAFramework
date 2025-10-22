package stepdefinations;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.ContextMenuPage;
import pages.HideAndShowPage;

public class HideAndShowStep {
    HideAndShowPage hideAndShowPage = new HideAndShowPage();



    @Then("there should be two {string} buttons and two text boxes visible")
    public void thereShouldBeTwoButtonsAndTwoTextBoxesVisible(String hideButton) {
        hideAndShowPage.thereShouldBeTwoButtonsAndTwoTextBoxesVisible(hideButton);
    }

    @When("the second {string} button is clicked")
    public void theSecondButtonIsClicked(String button) {
        hideAndShowPage.theSecondButtonIsClicked(button);
    }

    @Then("the corresponding text box should be hidden and the button text should change to {string}")
    public void theCorrespondingTextBoxShouldBeHiddenAndTheButtonTextShouldChangeTo(String buttonText) {
        hideAndShowPage.theCorrespondingTextBoxShouldBeHiddenAndTheButtonTextShouldChangeTo(buttonText);
    }

    @When("the user clicks Show button")
    public void theUserClicksShowButton() {
        hideAndShowPage.theUserClicksShowButton();
    }

    @Then("the text box should be visible again and the button text should be {string}")
    public void theTextBoxShouldBeVisibleAgainAndTheButtonTextShouldBe(String buttonText) {
        hideAndShowPage.theSecondTextBoxShouldBeVisible(buttonText);
    }
}
