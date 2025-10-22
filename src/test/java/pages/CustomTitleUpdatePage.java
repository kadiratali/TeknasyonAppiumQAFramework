package pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.CustomTitlePageConstants.*;

/**
 * Represents the Custom Title Update page of the application.
 * <p>
 * This page allows validation and interaction with the custom title section,
 * including verifying default texts, updating text boxes, and checking that
 * updates are properly reflected in both the text fields and navigation bar.
 * </p>
 */
public class CustomTitleUpdatePage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(CustomTitleUpdatePage.class);

    private String leftChangeText;
    private String rightChangeText;

    /**
     * Verifies that all default text boxes and navigation bar elements are visible and contain the correct default values.
     * <p>
     * This method checks both left and right headers and text fields to ensure that
     * the default texts match the expected constants.
     * </p>
     */
    public void theDefaultTextBoxAndNavigationBarTextsAreDisplayed() {
        assertVisible(CUSTOM_TITLE_DEFAULT_HEADER_LEFT, "The default header left is not displayed.");
        assertVisible(CUSTOM_TITLE_DEFAULT_HEADER_RIGHT, "The default header right is not displayed.");
        assertVisible(CUSTOM_TITLE_DEFAULT_LEFT_TEXT, "The default text left box is not displayed.");
        assertVisible(CUSTOM_TITLE_DEFAULT_RIGHT_TEXT, "The default text right box is not displayed.");

        assertEquals(getText(CUSTOM_TITLE_DEFAULT_HEADER_LEFT), DEFAULT_HEADER_LEFT_TEXT, "The default header left text is not correct.");
        assertEquals(getText(CUSTOM_TITLE_DEFAULT_HEADER_RIGHT), DEFAULT_HEADER_RIGHT_TEXT, "The default header right text is not correct.");

        logger.info("The default text box and navigation bar texts are displayed.");
    }

    /**
     * Updates the specified text box (left or right) with a new text value.
     * <p>
     * This method determines which text box to interact with based on the provided side,
     * updates its content, and clicks the corresponding 'Change' button to apply the update.
     * </p>
     *
     * @param textBox specifies which text box to update ("left" or "right")
     * @param text    the new text value to be entered into the text box
     */
    public void theUserUpdatesTheTextBoxTo(String textBox, String text) {
        By textBoxLocator = textBox.equals("left") ? CUSTOM_TITLE_DEFAULT_LEFT_TEXT : CUSTOM_TITLE_DEFAULT_RIGHT_TEXT;
        By changeButton = textBox.equals("left") ? CUSTOM_TITLE_CHANGE_LEFT_BTN : CUSTOM_TITLE_CHANGE_RIGHT_BTN;

        if (textBox.equals("left")) {
            setLeftChangeText(text);
        } else {
            setRightChangeText(text);
        }

        sendKeys(textBoxLocator, text);
        clickElement(findElement(changeButton), 5);

        logger.info("The user updates the {} text box to {}.", textBox, text);
    }

    /**
     * Verifies that the updated text values are reflected in both text boxes and navigation bar headers.
     * <p>
     * This method ensures consistency between the input fields and the displayed titles
     * after text updates are made by the user.
     * </p>
     */
    public void theUpdatedTextsAreShownInAllTextFieldsAndNavigationBar() {
        assertEquals(getText(CUSTOM_TITLE_DEFAULT_HEADER_LEFT), getLeftChangeText(), "The updated header left text is not correct.");
        assertEquals(getText(CUSTOM_TITLE_DEFAULT_HEADER_RIGHT), getRightChangeText(), "The updated header right text is not correct.");
        assertEquals(getText(CUSTOM_TITLE_DEFAULT_LEFT_TEXT), getLeftChangeText(), "The updated text left box is not correct.");
        assertEquals(getText(CUSTOM_TITLE_DEFAULT_RIGHT_TEXT), getRightChangeText(), "The updated text right box is not correct.");

        logger.info("The updated texts are shown in all text fields and navigation bar.");
    }

    // GETTER AND SETTER METHODS

    /**
     * Gets the updated text value entered in the left text box.
     *
     * @return the left text box's updated value
     */
    public String getLeftChangeText() {
        return leftChangeText;
    }

    /**
     * Sets the updated text value for the left text box.
     *
     * @param leftChangeText the new text value for the left text box
     */
    public void setLeftChangeText(String leftChangeText) {
        this.leftChangeText = leftChangeText;
    }

    /**
     * Gets the updated text value entered in the right text box.
     *
     * @return the right text box's updated value
     */
    public String getRightChangeText() {
        return rightChangeText;
    }

    /**
     * Sets the updated text value for the right text box.
     *
     * @param rightChangeText the new text value for the right text box
     */
    public void setRightChangeText(String rightChangeText) {
        this.rightChangeText = rightChangeText;
    }
}
