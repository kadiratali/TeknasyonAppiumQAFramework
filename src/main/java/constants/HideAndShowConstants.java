package constants;

import org.openqa.selenium.By;

public class HideAndShowConstants {

    private HideAndShowConstants() {
    }

    public static final By HIDE_BUTTON_ONE = By.id("io.appium.android.apis:id/frag1hide");
    public static final By HIDE_BUTTON_TWO = By.id("io.appium.android.apis:id/frag2hide");
    public static final By INITIAL_TEXT_1 = By.xpath("(//android.widget.EditText[@content-desc='Initial text.'])[1]");
    public static final By INITIAL_TEXT_2 = By.xpath("(//android.widget.EditText[@content-desc='Initial text.'])[2]");
}
