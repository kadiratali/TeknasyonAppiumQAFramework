package constants;

import org.openqa.selenium.By;

public class TabMenuConstants {
    private TabMenuConstants() {
    }

    public static By TAB_LST_MENU(String menuName) {
        return By.xpath("//android.widget.TextView[@content-desc='" + menuName + "']");
    }

    public static By TABS_MENU_LIST_DYNAMIC(String menuName) {
        return By.xpath("//android.widget.TextView[@content-desc='" + menuName + "']");
    }

    public static By SCROLLABLE_TABS(String tabName) {
        return By.xpath("//android.widget.TextView[@resource-id='android:id/title' and @text='" + tabName + "']");
    }

    public static final By SCROLL_VIEW_CLASS = By.className("android.widget.HorizontalScrollView");

    public static By SCROLL_TABS_TEXT(String tabName) {
        return By.xpath("//android.widget.TextView[@text='Content for tab with tag " + tabName + "']");
    }

}
