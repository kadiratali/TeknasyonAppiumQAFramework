package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.HideAndShowConstants.*;

/**
 * Represents the Hide and Show page of the application.
 * <p>
 * This class provides methods to verify the visibility of buttons and text boxes,
 * handle user interactions with hide/show buttons, and validate UI behavior
 * when elements are hidden or displayed.
 * </p>
 */
public class HideAndShowPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(HideAndShowPage.class);

    /**
     * Verifies that two buttons and two text boxes are visible on the page.
     * <p>
     * This method checks the presence of both hide buttons and both text boxes,
     * ensuring that the UI is displayed in its initial, expected state.
     * </p>
     *
     * @param hideButton the name of the hide button to verify (used for logging and validation)
     */
    public void thereShouldBeTwoButtonsAndTwoTextBoxesVisible(String hideButton) {
        assertVisible(HIDE_BUTTON_ONE, "The " + hideButton + " button is not displayed.");
        assertVisible(HIDE_BUTTON_TWO, "The second " + hideButton + " button is not displayed.");
        assertVisible(INITIAL_TEXT_1, "The initial text box is not displayed.");
        assertVisible(INITIAL_TEXT_2, "The second initial text box is not displayed.");
        logger.info("There should be two buttons and two text boxes visible.");
    }

    /**
     * Clicks the second hide/show button on the page.
     * <p>
     * This action triggers the visibility toggle of the corresponding text box.
     * </p>
     *
     * @param button the name of the button to be clicked (used for logging and validation)
     */
    public void theSecondButtonIsClicked(String button) {
        assertVisible(HIDE_BUTTON_TWO, "The second " + button + " button is not displayed.");
        clickElement(HIDE_BUTTON_TWO);
        logger.info("The second {} button is clicked.", button);
    }

    /**
     * Verifies that the corresponding text box is hidden
     * and the button text has changed to the expected value.
     * <p>
     * Typically used after clicking the hide button to ensure the correct behavior
     * (e.g., the button text changes from "Hide" to "Show" and the text box disappears).
     * </p>
     *
     * @param buttonText the expected new text of the button (e.g., "Show")
     */
    public void theCorrespondingTextBoxShouldBeHiddenAndTheButtonTextShouldChangeTo(String buttonText) {
        assertVisible(HIDE_BUTTON_TWO, "The second " + buttonText + " button is not displayed.");
        assertEquals(getText(HIDE_BUTTON_TWO), buttonText, "The " + buttonText + " button text is not correct.");
        assertFalse(isElementVisible(INITIAL_TEXT_2), "The second initial text box is still displayed.");
        logger.info("The corresponding text box should be hidden and the button text should change to {}.", buttonText);
    }

    /**
     * Clicks the "Show" button to display the hidden text box again.
     * <p>
     * This method checks that the button text equals "Show" before performing the click.
     * </p>
     */
    public void theUserClicksShowButton() {
        assertVisible(HIDE_BUTTON_TWO, "The second show button is not displayed.");
        if (getText(HIDE_BUTTON_TWO).equals("Show")) {
            clickElement(HIDE_BUTTON_TWO);
        }
        logger.info("The user clicks the show button.");
    }

    /**
     * Verifies that the second text box becomes visible after the "Show" button is clicked.
     * <p>
     * This ensures that the text box reappears and the button text is correctly updated.
     * </p>
     *
     * @param buttonText the expected button text after showing the text box (e.g., "Hide")
     */
    public void theSecondTextBoxShouldBeVisible(String buttonText) {
        assertVisible(HIDE_BUTTON_TWO, "The second " + buttonText + " button is not displayed.");
        assertEquals(getText(HIDE_BUTTON_TWO), buttonText, "The " + buttonText + " button text is not correct.");
        assertVisible(INITIAL_TEXT_2, "The second initial text box is not displayed.");
        logger.info("The second text box should be visible.");
    }
}
