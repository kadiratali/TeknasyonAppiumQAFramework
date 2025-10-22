package constants;

import org.openqa.selenium.By;

public class ContextMenuConstants {

    private ContextMenuConstants() {
    }

    public static final By CONTEXT_MENU_LONG_PRESS_ME_BTN = By.id("io.appium.android.apis:id/long_press");

    public static By CONTEXT_MENU_OPTIONS(String menuName) {
        return By.xpath("//android.widget.TextView[@resource-id='android:id/title' and @text='" + menuName + "']");
    }
}
