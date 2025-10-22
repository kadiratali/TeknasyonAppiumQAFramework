package pages;

import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Base page class that provides common functionality for all page objects.
 * Contains utility methods for element interactions, waits, assertions, and gestures.
 */
public abstract class BasePage {

    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected static final int DEFAULT_TIMEOUT = 10;
    private static final int DEFAULT_IMPLICIT_WAIT = 10;
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    /**
     * Constructor that initializes the driver and PageFactory.
     * Sets up WebDriverWait with a default timeout of 10 seconds.
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Waits for a specific condition to be met within the provided timeout.
     *
     * @param condition The ExpectedCondition to wait for
     * @param timeout   Duration in seconds to wait for the condition
     * @param <T>       Type parameter for the expected condition result
     * @return The condition result once fulfilled, or null if timeout occurs
     */
    private <T> T waitForCondition(ExpectedCondition<T> condition, int timeout) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        try {
            return wait.until(condition);
        } catch (Exception e) {
            logger.info("Condition not met: " + e.getMessage());
            return null;
        }
    }

    /**
     * Clicks on a WebElement after waiting for it to be clickable.
     *
     * @param element The WebElement to click
     * @param timeout Timeout duration in seconds
     */
    public void clickElement(WebElement element, int timeout) {
        if (waitForCondition(ExpectedConditions.elementToBeClickable(element), timeout) != null) {
            element.click();
        }
    }

    /**
     * Finds an element using the default timeout.
     *
     * @param by Locator strategy for the element
     * @return The located WebElement
     */
    public WebElement findElement(By by) {
        return findElement(by, DEFAULT_TIMEOUT);
    }

    /**
     * Finds an element by its locator and waits for it to be visible.
     *
     * @param by      Locator strategy for the element
     * @param timeout Timeout duration in seconds
     * @return The located WebElement, or null if not found within timeout
     */
    public WebElement findElement(By by, int timeout) {
        return waitForCondition(ExpectedConditions.visibilityOfElementLocated(by), timeout);
    }

    /**
     * Asserts that two values are equal.
     *
     * @param actual   The actual value
     * @param expected The expected value
     * @param message  Error message to display if assertion fails
     * @param <T>      Type of the values being compared
     */
    public <T> void assertEquals(T actual, T expected, String message){
        Assert.assertEquals(actual, expected, message);
    }

    /**
     * Gets the visible text of an element.
     *
     * @param locator The element's locator
     * @return The text content of the element
     */
    public String getText(By locator){
        return findElement(locator, 5).getText();
    }

    /**
     * Fails the test with the specified message.
     *
     * @param message The failure message
     */
    public void assertFail(String message){
        Assert.fail(message);
    }

    /**
     * Asserts that a condition is true.
     *
     * @param condition    The boolean condition to check
     * @param errorMessage Error message to display if assertion fails
     */
    public void assertTrue(boolean condition, String errorMessage){
        Assert.assertTrue(condition, errorMessage);
    }

    /**
     * Asserts that a condition is false.
     *
     * @param condition    The boolean condition to check
     * @param errorMessage Error message to display if assertion fails
     */
    public void assertFalse(boolean condition, String errorMessage){
        Assert.assertFalse(condition, errorMessage);
    }

    /**
     * Asserts that an element is visible on the screen.
     *
     * @param by           Locator strategy for the element
     * @param errorMessage Error message to display if element is not visible
     * @param timeout      Optional timeout in seconds (uses default if not provided)
     */
    public void assertVisible(By by, String errorMessage, int... timeout){
        int timeoutFinal = timeout.length == 0 ? DEFAULT_TIMEOUT : timeout[0];
        assertTrue(isElementVisible(by, timeoutFinal), errorMessage);
    }

    /**
     * Checks if an element is visible within the specified timeout.
     *
     * @param by      Locator strategy for the element
     * @param timeout Optional timeout in seconds (uses default if not provided)
     * @return true if element is visible, false otherwise
     */
    protected boolean isElementVisible(By by, int... timeout){
        int time = timeout.length == 0 ? DEFAULT_TIMEOUT : timeout[0];

        setImplicitWait(time);
        boolean isVisible;
        try {
            wait(time).until(ExpectedConditions.visibilityOfElementLocated(by));
            isVisible = true;
        }catch (Exception e){
            isVisible = false;
        }
        return isVisible;
    }

    /**
     * Creates a WebDriverWait with the specified timeout.
     *
     * @param second Timeout duration in seconds
     * @return WebDriverWait instance
     */
    public WebDriverWait wait(int second){
        return new WebDriverWait(driver, Duration.ofSeconds(second));
    }

    /**
     * Sets the implicit wait timeout for the driver.
     *
     * @param second Timeout duration in seconds
     */
    private void setImplicitWait(int second){
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(second));
        }catch (Exception e){
            assertFail("Driver timeout: ".formatted(e));
        }
    }

    /**
     * Clears and sends keys to an element.
     *
     * @param by   Locator strategy for the element
     * @param text Text to send to the element
     */
    public void sendKeys(By by, String text){
        WebElement element = findElement(by);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Clicks an element after waiting for it to be visible.
     * Ignores StaleElementReferenceException during the wait.
     *
     * @param by Locator strategy for the element
     */
    public void clickElement(By by){
        try {
            wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOfElementLocated(by)).click();
        }catch (Exception e){
            assertFail("Element click error: " + e.getMessage());
        }
    }

    /**
     * Gets a random index from a list.
     *
     * @param list The list to get random index from
     * @return Random index between 0 and list size - 1
     * @throws IllegalArgumentException if list is null or empty
     */
    public int getRandomIndexFromList(List<?> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        return ThreadLocalRandom.current().nextInt(0, list.size());
    }

    /**
     * Finds multiple elements matching the locator.
     *
     * @param by Locator strategy for the elements
     * @return List of WebElements, or empty list if none found or error occurs
     */
    public List<WebElement> findElements(By by) {
        try {
            List<WebElement> elements = driver.findElements(by);
            if (elements == null || elements.isEmpty()) {
                logger.info("No elements found for locator: " + by);
            }
            return elements;
        } catch (Exception e) {
            logger.info("Error finding elements by: " + by + " -> " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Performs a long press gesture on an element.
     *
     * @param element The element to long press
     * @param seconds Duration of the press in seconds
     */
    public void longPress(WebElement element, int seconds) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence longPress = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(),
                        element.getLocation().getX(), element.getLocation().getY()))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new org.openqa.selenium.interactions.Pause(finger, Duration.ofSeconds(seconds)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(longPress));
    }

    /**
     * Opens the notification bar on Android devices.
     * Does nothing if the driver is not an AndroidDriver.
     */
    public void openNotificationBar() {
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).openNotifications();
            logger.info("Notification bar opened on Android device.");
        } else {
            logger.info("This feature is only available on Android devices.");
        }
    }

    /**
     * Enum for swipe directions.
     */
    public enum Direction {
        /** Vertical swipe from bottom to top */
        VERTICAL,
        /** Horizontal swipe from left to right */
        HORIZONTAL_LEFT_TO_RIGHT,
        /** Horizontal swipe from right to left */
        HORIZONTAL_RIGHT_TO_LEFT
    }

    /**
     * Swipes the screen in the specified direction until the element becomes visible.
     * Uses full screen coordinates for swiping.
     *
     * @param element   The element to wait for visibility
     * @param maxSwipe  Maximum number of swipe attempts
     * @param direction Direction to swipe (VERTICAL, HORIZONTAL_LEFT_TO_RIGHT, HORIZONTAL_RIGHT_TO_LEFT)
     * @throws IllegalArgumentException if direction is invalid
     */
    public void swipeUntilElementVisible(WebElement element, int maxSwipe, Direction direction) {
        int alreadySwiped = 0;
        int screenWidth = driver .manage().window().getSize().width;
        int screenHeight = driver.manage().window().getSize().height;

        while (alreadySwiped < maxSwipe) {
            try {
                if (element.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                // Element not visible, continue swiping
            }

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            int startX, startY, endX, endY;

            switch (direction) {
                case VERTICAL:
                    startX = screenWidth / 2;
                    startY = (int) (screenHeight * 0.8);
                    endX = startX;
                    endY = (int) (screenHeight * 0.2);
                    break;
                case HORIZONTAL_LEFT_TO_RIGHT:
                    startY = screenHeight / 2;
                    startX = (int) (screenWidth * 0.1);
                    endX = (int) (screenWidth * 0.9);
                    endY = startY;
                    break;
                case HORIZONTAL_RIGHT_TO_LEFT:
                    startY = screenHeight / 2;
                    startX = (int) (screenWidth * 0.9);
                    endX = (int) (screenWidth * 0.1);
                    endY = startY;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid swipe direction");
            }

            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(swipe));
            alreadySwiped++;
        }
    }

    /**
     * Pauses execution for the specified number of seconds.
     *
     * @param seconds Number of seconds to wait
     */
    public void waitSeconds(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        }catch (Exception e){
            assertFail("Wait seconds error: " + e.getMessage());
        }
    }

    /**
     * Scrolls right within a ScrollView element.
     * Performs a swipe gesture from right to left within the element's boundaries.
     *
     * @param scrollView      The ScrollView element to scroll within
     * @param startPercentage Starting X position as percentage of element width (0-100)
     * @param endPercentage   Ending X position as percentage of element width (0-100)
     * @param durationMs      Duration of the swipe gesture in milliseconds
     */
    public void scrollRight(WebElement scrollView, int startPercentage, int endPercentage, int durationMs) {
        int startX = scrollView.getLocation().getX() + (scrollView.getSize().getWidth() * startPercentage / 100);
        int endX = scrollView.getLocation().getX() + (scrollView.getSize().getWidth() * endPercentage / 100);
        int startY = scrollView.getLocation().getY() + (scrollView.getSize().getHeight() / 2);

        performSwipe(startX, startY, endX, startY, durationMs);
    }

    /**
     * Performs a swipe gesture between two points.
     * Uses W3C Actions API with PointerInput for touch interactions.
     *
     * @param startX     Starting X coordinate
     * @param startY     Starting Y coordinate
     * @param endX       Ending X coordinate
     * @param endY       Ending Y coordinate
     * @param durationMs Duration of the swipe in milliseconds
     */
    private void performSwipe(int startX, int startY, int endX, int endY, int durationMs) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }
}