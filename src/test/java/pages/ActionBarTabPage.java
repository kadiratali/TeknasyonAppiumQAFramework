package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.ActionBarPageContants.*;

public class ActionBarTabPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ActionBarTabPage.class);

    public void theOptionShouldBeInactive(String tabBar) {
        if (tabBar.equals("Toggle tab mode") && isElementVisible(ACTION_BAR_TABS_PAGE_HEADER, 5)) {
            assertVisible(ACTION_BAR_TABS_TOGGLE_TAB_MODU_BTN, "The toggle tabs button is not displayed.");
            clickElement(ACTION_BAR_TABS_TOGGLE_TAB_MODU_BTN);
            boolean toggleControl = isElementVisible(ACTION_BAR_TABS_PAGE_HEADER, 5);
            assertFalse(toggleControl, "The toggle tabs button is still displayed.");
        }
        logger.info("The {} option should be inactive.", tabBar);
    }

    public void theUserAddsNewTabs(int numberOfTabs){
        assertVisible(ACTION_BAR_TABS_ADD_NEW_TAB, "The add new tab button is not displayed.");
        clickElement(ACTION_BAR_TABS_ADD_NEW_TAB);
        logger.info("The user adds {} new tabs.", numberOfTabs);
        assertFail("Redirected to the action bar page.");
    }
}
