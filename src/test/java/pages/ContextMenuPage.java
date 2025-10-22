package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.ContextMenuConstants.CONTEXT_MENU_LONG_PRESS_ME_BTN;
import static constants.ContextMenuConstants.contextMenuOptions;

/**
 * Represents the Context Menu page of the application.
 * <p>
 * This class provides methods to perform user interactions
 * such as long pressing a button and verifying the visibility
 * of context menu options (e.g., Option A and Option B).
 * </p>
 */
public class ContextMenuPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(ContextMenuPage.class);

    /**
     * Performs a long press action on the specified button.
     * <p>
     * This method ensures that the target button is visible,
     * performs a long press for the defined duration, and logs the action.
     * </p>
     *
     * @param longPressButton the name of the button to be long pressed
     */
    public void theUserLongPressesTheButton(String longPressButton) {
        assertVisible(CONTEXT_MENU_LONG_PRESS_ME_BTN, "The long press me button is not displayed.");
        longPress(findElement(CONTEXT_MENU_LONG_PRESS_ME_BTN), 5);
        logger.info("The user long presses the {} button.", longPressButton);
    }

    /**
     * Verifies that the given context menu options are visible after the long press action.
     * <p>
     * Typically used to confirm that options such as "Menu A" and "Menu B" appear
     * after performing a long press on the target element.
     * </p>
     *
     * @param menuA the first context menu option to verify
     * @param menuB the second context menu option to verify
     */
    public void verifyMenuAAndBVisible(String menuA, String menuB) {
        assertVisible(contextMenuOptions(menuA), "The " + menuA + " option is not displayed.");
        assertVisible(contextMenuOptions(menuB), "The " + menuB + " option is not displayed.");
        logger.info("The {} and {} options are displayed.", menuA, menuB);
    }
}
