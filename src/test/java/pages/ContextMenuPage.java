package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.ContextMenuConstants.CONTEXT_MENU_LONG_PRESS_ME_BTN;
import static constants.ContextMenuConstants.CONTEXT_MENU_OPTIONS;

public class ContextMenuPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(ContextMenuPage.class);


    public void theUserLongPressesTheButton(String longPressButton) {
        assertVisible(CONTEXT_MENU_LONG_PRESS_ME_BTN, "The long press me button is not displayed.");
        longPress(findElement(CONTEXT_MENU_LONG_PRESS_ME_BTN), 5);
        logger.info("The user long presses the {} button.", longPressButton);
    }

    public void verifyMenuAAndBVisible(String menuA, String menuB) {
        assertVisible(CONTEXT_MENU_OPTIONS(menuA), "The " + menuA + " option is not displayed.");
        assertVisible(CONTEXT_MENU_OPTIONS(menuB), "The " + menuB + " option is not displayed.");
        logger.info("The {} and {} options are displayed.", menuA, menuB);
    }
}
