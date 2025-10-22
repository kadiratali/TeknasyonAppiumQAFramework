package pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static constants.NotificationControlConstants.*;

public class NotificationControlPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(NotificationControlPage.class);

    private String notificationHeader;
    private String notificationText;

    public void clickShowNotification(String showNotification) {
        assertVisible(INCOMING_MESSAGE_SHOW_APP_NOTIFICATION, "The show app notification button is not displayed.");
        clickElement(INCOMING_MESSAGE_SHOW_APP_NOTIFICATION);
        logger.info("The user clicks the {} button.", showNotification);
    }

    public void theNotificationBarShouldBeDisplayed() {
        logger.info("The notification bar should be displayed.");
        wait(2);
        openNotificationBar();
        assertVisible(NOTIFICATION_HEADER, "The notification header is not displayed.");
    }

    public void theNotificationIsClicked() {
        assertVisible(NOTIFICATION_DETAIL_HEADER_TXT, "The notification header is not displayed.");
        assertVisible(NOTIFICATION_DETAIL_MSG, "The notification message is not displayed.");
        setNotificationHeader(getText(NOTIFICATION_DETAIL_HEADER_TXT));
        setNotificationText(getText(NOTIFICATION_DETAIL_MSG));
        clickElement(NOTIFICATION_HEADER);
        logger.info("The notification is clicked.");
    }

    public void theNotificationDetailPageShouldOpen(){
        logger.info("The notification detail page should open.");
        wait(2);
        assertVisible(NOTIFICATION_PAGE_FROM_INFO, "The notification from is not displayed.");
        assertVisible(NOTIFICATION_PAGE_NOTIFICATION_TXT, "The notification message is not displayed.");
        assertEquals(getNotificationHeader(), getText(NOTIFICATION_PAGE_FROM_INFO), "The notification header text is not correct.");
        assertEquals(getNotificationText(), getText(NOTIFICATION_PAGE_NOTIFICATION_TXT), "The notification message text is not correct.");
        logger.info("The notification detail page should open.");
    }

    public void theTextInTheNotificationBarShouldMatchTheTextInTheDetailPage(){
        logger.info("The text in the notification bar should match the text in the detail page.");
        assertVisible(NOTIFICATION_PAGE_NOTIFICATION_MSG, "The notification header is not displayed.");
        assertVisible(NOTIFICATION_PAGE_IMG, "The notification image is not displayed.");
    }

    //GETTER AND SETTER METHODS
    public String getNotificationHeader() {
        return notificationHeader;
    }

    public void setNotificationHeader(String notificationHeader) {
        this.notificationHeader = notificationHeader;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
}
