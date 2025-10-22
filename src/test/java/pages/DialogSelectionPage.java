package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.DialogSelectionPageConstants.*;

/**
 * Represents the Dialog Selection page of the application.
 * <p>
 * This class provides methods to handle user interactions with dialog lists,
 * including selecting specific or random options and verifying alert messages
 * that confirm user selections.
 * </p>
 */
public class DialogSelectionPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(DialogSelectionPage.class);

    private String listDialogOption;

    /**
     * Selects a specific option from the dialog list.
     * <p>
     * This method verifies that the given option is visible in the list,
     * clicks on it, and logs the selection.
     * </p>
     *
     * @param option the name of the dialog list option to be selected
     */
    public void theUserSelectTheOption(String option) {
        assertVisible(dialogSelectionList(option), "The " + option + " option is not displayed.");
        clickElement(dialogSelectionList(option));
        logger.info("The user selects the {} option.", option);
    }

    /**
     * Selects a random option from the given dialog list.
     * <p>
     * This method ensures that the list of options is visible, randomly picks one option,
     * stores its text value for later validation, and clicks on it.
     * </p>
     *
     * @param listOption the name or type of list (for logging purposes)
     */
    public void theUserSelectsARandomElementFromThe(String listOption) {
        assertVisible(LIST_DIALOG_OPTION_LST, "The list of options is not displayed.");
        int randListOption = getRandomIndexFromList(findElements(LIST_DIALOG_OPTION_LST));
        String listOptionText = findElements(LIST_DIALOG_OPTION_LST).get(randListOption).getText();
        clickElement(findElements(LIST_DIALOG_OPTION_LST).get(randListOption), 5);
        setListDialogOption(listOptionText);
        logger.info("The user selects a random element from the {} list.", listOption);
    }

    /**
     * Verifies that the alert displays the correct element name and position
     * after the user selects an option from the dialog list.
     * <p>
     * This method checks that the alert message contains the previously selected
     * option text stored in {@code listDialogOption}.
     * </p>
     */
    public void theAlertDisplaysTheCorrectElementNameAndPosition() {
        assertVisible(LIST_DIALOG_ALERT_MSG, "The alert is not displayed.");
        assertTrue(getText(LIST_DIALOG_ALERT_MSG).contains(getListDialogOption()),
                "The alert does not display the correct element name.");
        logger.info("The alert displays the correct element name and position.");
    }

    // GETTER AND SETTER METHODS

    /**
     * Returns the last selected dialog option text.
     *
     * @return the name of the selected dialog option
     */
    public String getListDialogOption() {
        return listDialogOption;
    }

    /**
     * Sets the text of the last selected dialog option.
     *
     * @param listDialogOption the text of the selected dialog option
     */
    public void setListDialogOption(String listDialogOption) {
        this.listDialogOption = listDialogOption;
    }
}
