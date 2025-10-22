package constants;

public class CommonPageConstants {

    private CommonPageConstants() {
    }
    // Locator constants
    public static final String MENU_ITEM_XPATH_TEMPLATE = "//android.widget.TextView[@content-desc='%s']";

    // Key for DataTable item in Cucumber
    public static final String MENU_ITEM_KEY = "MenuItem";

    // Error Messages
    public static final String MENU_ITEM_NOT_FOUND_ERROR = "Menu item '%s' could not be found on the screen.";
}
