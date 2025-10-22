package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.HideAndShowConstants.*;

public class HideAndShowPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HideAndShowPage.class);

    public void thereShouldBeTwoButtonsAndTwoTextBoxesVisible(String hideButton) {
        assertVisible(HIDE_BUTTON_ONE, "The " + hideButton + " button is not displayed.");
        assertVisible(HIDE_BUTTON_TWO, "The second " + hideButton + " button is not displayed.");
        assertVisible(INITIAL_TEXT_1, "The initial text box is not displayed.");
        assertVisible(INITIAL_TEXT_2, "The second initial text box is not displayed.");
        logger.info("There should be two buttons and two text boxes visible.");
    }

    public void theSecondButtonIsClicked(String button) {
        assertVisible(HIDE_BUTTON_TWO, "The second " + button + " button is not displayed.");
        clickElement(HIDE_BUTTON_TWO);
        logger.info("The second {} button is clicked.", button);
    }

    public void theCorrespondingTextBoxShouldBeHiddenAndTheButtonTextShouldChangeTo(String buttonText) {
        assertVisible(HIDE_BUTTON_TWO, "The second " + buttonText + " button is not displayed.");
        assertEquals(getText(HIDE_BUTTON_TWO), buttonText, "The " + buttonText + " button text is not correct.");
        assertFalse(isElementVisible(INITIAL_TEXT_2), "The second initial text box is still displayed.");
        logger.info("The corresponding text box should be hidden and the button text should change to {}.", buttonText);
    }

    public void theUserClicksShowButton() {
        assertVisible(HIDE_BUTTON_TWO, "The second show button is not displayed.");
        if (getText(HIDE_BUTTON_TWO).equals("Show")) {
            clickElement(HIDE_BUTTON_TWO);
        }
        logger.info("The user clicks the show button.");
    }

    public void theSecondTextBoxShouldBeVisible(String buttonText) {
        assertVisible(HIDE_BUTTON_TWO, "The second " + buttonText + " button is not displayed.");
        assertEquals(getText(HIDE_BUTTON_TWO), buttonText, "The " + buttonText + " button text is not correct.");
        assertVisible(INITIAL_TEXT_2, "The second initial text box is not displayed.");
        logger.info("The second text box should be visible.");
    }
}
