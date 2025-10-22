package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.TabMenuConstants.*;

/**
 * Represents the Tab Menu page of the application.
 * <p>
 * This class provides methods to interact with tab menus, including scrolling
 * to a menu, navigating to a menu, clicking tabs, and verifying the content
 * displayed in opened tabs.
 * </p>
 */
public class TabMenuPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(TabMenuPage.class);

    /**
     * Scrolls vertically until the specified menu is visible, then clicks it.
     * <p>
     * This method waits briefly after scrolling to ensure that the element is fully loaded.
     * </p>
     *
     * @param tabMenu the name of the menu to scroll to and select
     */
    public void theUserScrollsToMenu(String tabMenu) {
        swipeUntilElementVisible(findElement(tabListMenu(tabMenu)), 2, Direction.VERTICAL);
        waitSeconds(5);
        clickElement(tabListMenu(tabMenu));
        logger.info("The user scrolls to the {} menu.", tabMenu);
    }

    /**
     * Navigates directly to the specified tab menu by verifying its visibility and clicking it.
     *
     * @param menu the name of the menu to navigate to
     */
    public void theUserNavigatesToMenu(String menu) {
        assertVisible(tabListMenu(menu), "The {} menu is not displayed.");
        clickElement(tabListMenu(menu));
        logger.info("The user navigates to the {} menu.", menu);
    }

    /**
     * Clicks the specified tab in a scrollable tab view.
     * <p>
     * This method attempts to swipe right until the desired tab becomes visible
     * or until the maximum number of swipes is reached, then clicks it.
     * </p>
     *
     * @param tabNumber the identifier or name of the tab to click
     */
    public void theUserClicksTheTab(String tabNumber) {
        int alreadySwiped = 0;
        int maxSwipe = 10;

        while (alreadySwiped < maxSwipe) {
            try {
                if (isElementVisible(scrollableTabs(tabNumber), 5)) {
                    break;
                }
            } catch (Exception e) {
                // If the element is not visible, swipe to the right
            }
            scrollRight(findElement(SCROLL_VIEW_CLASS), 85, 15, 700);
            alreadySwiped++;
        }

        clickElement(scrollableTabs(tabNumber));
        logger.info("The user clicks the {} tab.", tabNumber);
    }

    /**
     * Verifies that the information displayed in the opened tab belongs to the specified tab name.
     * <p>
     * This method asserts that the tab content matches the expected formatted text for the tab.
     * </p>
     *
     * @param tabName the name of the tab whose content should be verified
     */
    public void theOpenedTabInformationShouldBelongTo(String tabName) {
        assertVisible(scrollTabsText(tabName), "The opened tab information is not displayed.");
        String tabTxt = "Content for tab with tag %s".formatted(tabName);
        assertEquals(getText(scrollTabsText(tabName)), tabTxt, "The opened tab information is not correct.");
        logger.info("The opened tab information should belong to {}.", tabName);
    }
}
