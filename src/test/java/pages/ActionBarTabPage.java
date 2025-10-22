package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.ActionBarPageContants.*;

/**
 * Represents the Action Bar Tabs page and provides actions
 * to verify tab mode status and interact with tab elements.
 */
public class ActionBarTabPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ActionBarTabPage.class);

    /**
     * Verifies that the given option (e.g., "Toggle tab mode") is inactive.
     * <p>
     * If the "Toggle tab mode" option is visible, this method clicks on it
     * and asserts that it becomes inactive (i.e., no longer visible).
     * </p>
     *
     * @param tabBar the name of the option to verify, typically "Toggle tab mode"
     */
    public void theOptionShouldBeInactive(String tabBar) {
        if (tabBar.equals("Toggle tab mode") && isElementVisible(ACTION_BAR_TABS_PAGE_HEADER, 5)) {
            assertVisible(ACTION_BAR_TABS_TOGGLE_TAB_MODU_BTN, "The toggle tabs button is not displayed.");
            clickElement(ACTION_BAR_TABS_TOGGLE_TAB_MODU_BTN);
            boolean toggleControl = isElementVisible(ACTION_BAR_TABS_PAGE_HEADER, 5);
            assertFalse(toggleControl, "The toggle tabs button is still displayed.");
        }
        logger.info("The {} option should be inactive.", tabBar);
    }

    /**
     * Adds a specific number of new tabs on the Action Bar Tabs page.
     * <p>
     * This method verifies that the "Add new tab" button is visible, clicks it,
     * and logs the action. A failure is asserted if the user is redirected
     * to an unexpected page.
     * </p>
     *
     * @param numberOfTabs the number of new tabs the user intends to add
     */
    public void theUserAddsNewTabs(int numberOfTabs) {
        assertVisible(ACTION_BAR_TABS_ADD_NEW_TAB, "The add new tab button is not displayed.");
        clickElement(ACTION_BAR_TABS_ADD_NEW_TAB);
        logger.info("The user adds {} new tabs.", numberOfTabs);
        assertFail("Redirected to the action bar page.");
    }
}
