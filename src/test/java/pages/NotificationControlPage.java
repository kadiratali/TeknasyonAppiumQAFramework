package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.NotificationControlConstants.*;

/**
 * Represents the Notification Control page of the application.
 * <p>
 * This class provides methods to interact with app notifications,
 * including showing notifications, opening the notification bar,
 * clicking notifications, and verifying notification details.
 * </p>
 */
public class NotificationControlPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(NotificationControlPage.class);

    private String notificationHeader;
    private String notificationText;

    /**
     * Clicks the "Show App Notification" button to trigger a notification.
     *
     * @param showNotification the name of the button (used for logging)
     */
    public void clickShowNotification(String showNotification) {
        assertVisible(INCOMING_MESSAGE_SHOW_APP_NOTIFICATION, "The show app notification button is not displayed.");
        clickElement(INCOMING_MESSAGE_SHOW_APP_NOTIFICATION);
        logger.info("The user clicks the {} button.", showNotification);
    }

    /**
     * Verifies that the notification bar is displayed.
     * <p>
     * This method waits briefly, opens the notification bar,
     * and asserts that the notification header is visible.
     * </p>
     */
    public void theNotificationBarShouldBeDisplayed() {
        logger.info("The notification bar should be displayed.");
        wait(2);
        openNotificationBar();
        assertVisible(NOTIFICATION_HEADER, "The notification header is not displayed.");
    }

    /**
     * Clicks on a notification in the notification bar.
     * <p>
     * This method validates that the notification header and message are visible,
     * stores their text for later verification, and clicks the notification.
     * </p>
     */
    public void theNotificationIsClicked() {
        assertVisible(NOTIFICATION_DETAIL_HEADER_TXT, "The notification header is not displayed.");
        assertVisible(NOTIFICATION_DETAIL_MSG, "The notification message is not displayed.");
        setNotificationHeader(getText(NOTIFICATION_DETAIL_HEADER_TXT));
        setNotificationText(getText(NOTIFICATION_DETAIL_MSG));
        clickElement(NOTIFICATION_HEADER);
        logger.info("The notification is clicked.");
    }

    /**
     * Verifies that the notification detail page opens correctly.
     * <p>
     * This method checks that the "From" info and message are displayed,
     * and that the text matches the stored notification header and message.
     * </p>
     */
    public void theNotificationDetailPageShouldOpen() {
        logger.info("The notification detail page should open.");
        wait(2);
        assertVisible(NOTIFICATION_PAGE_FROM_INFO, "The notification from is not displayed.");
        assertVisible(NOTIFICATION_PAGE_NOTIFICATION_TXT, "The notification message is not displayed.");
        assertEquals(getNotificationHeader(), getText(NOTIFICATION_PAGE_FROM_INFO), "The notification header text is not correct.");
        assertEquals(getNotificationText(), getText(NOTIFICATION_PAGE_NOTIFICATION_TXT), "The notification message text is not correct.");
        logger.info("The notification detail page should open.");
    }

    /**
     * Verifies that the text and image in the notification detail page
     * match the original text in the notification bar.
     */
    public void theTextInTheNotificationBarShouldMatchTheTextInTheDetailPage() {
        logger.info("The text in the notification bar should match the text in the detail page.");
        assertVisible(NOTIFICATION_PAGE_NOTIFICATION_MSG, "The notification header is not displayed.");
        assertVisible(NOTIFICATION_PAGE_IMG, "The notification image is not displayed.");
    }

    // GETTER AND SETTER METHODS

    /**
     * Returns the stored notification header text.
     *
     * @return the notification header text
     */
    public String getNotificationHeader() {
        return notificationHeader;
    }

    /**
     * Sets the notification header text.
     *
     * @param notificationHeader the header text to store
     */
    public void setNotificationHeader(String notificationHeader) {
        this.notificationHeader = notificationHeader;
    }

    /**
     * Returns the stored notification message text.
     *
     * @return the notification message text
     */
    public String getNotificationText() {
        return notificationText;
    }

    /**
     * Sets the notification message text.
     *
     * @param notificationText the message text to store
     */
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
