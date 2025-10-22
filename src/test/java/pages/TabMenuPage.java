package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.TabMenuConstants.*;

public class TabMenuPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(TabMenuPage.class);


    public void theUserScrollsToMenu(String tabMenu) {
        swipeUntilElementVisible(findElement(tabListMenu(tabMenu)), 2, Direction.VERTICAL);
        waitSeconds(5);
        clickElement(tabListMenu(tabMenu));
        logger.info("The user scrolls to the {} menu.", tabMenu);
    }

    public void theUserNavigatesToMenu(String menu) {
        assertVisible(tabListMenu(menu), "The {} menu is not displayed.");
        clickElement(tabListMenu(menu));
        logger.info("The user navigates to the {} menu.", menu);
    }

    public void theUserClicksTheTab(String tabNumber){
        int alreadySwiped = 0;
        int maxSwipe = 10;
        while (alreadySwiped < maxSwipe) {
            try {
                if (isElementVisible(scrollableTabs(tabNumber), 5)) {
                    break;
                }
            } catch (Exception e) {
                // Element görünmüyorsa swipe yap
            }
            scrollRight(findElement(SCROLL_VIEW_CLASS), 85, 15, 700);
            alreadySwiped++;
        }

        clickElement(scrollableTabs(tabNumber));
        logger.info("The user clicks the {} tab.", tabNumber);
    }

    public void theOpenedTabInformationShouldBelongTo(String tabName){
        assertVisible(scrollTabsText(tabName), "The opened tab information is not displayed.");
        String tabTxt = "Content for tab with tag %s".formatted(tabName);
        assertEquals(getText(scrollTabsText(tabName)), tabTxt, "The opened tab information is not correct.");
        logger.info("The opened tab information should belong to {}.", tabName);
    }
}

