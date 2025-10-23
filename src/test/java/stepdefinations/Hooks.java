package stepdefinations;

import com.google.common.collect.ImmutableMap;
import config.AppSettings;
import driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.json.simple.JSONObject;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private static final String TARGET_TAG = "@ResetAppBeforeTest";

    // ResetAppBeforeTest için gerekli ayarları bir kez yükle
    private static final JSONObject platformSettings = AppSettings.getCapabilities();
    private static final String APP_PACKAGE = (String) platformSettings.get("appPackage");
    private static final String APP_PATH = (String) platformSettings.get("app");

    /**
     * Her senaryodan ÖNCE çalışır.
     * @param scenario Çalışacak olan senaryo
     */
    @Before
    public void setUp(Scenario scenario) {
        logger.info("Senaryo başlıyor: {} [Thread: {}]", scenario.getName(), Thread.currentThread().getId());

        DriverManager.initializeDriver();

        if (scenario.getSourceTagNames().contains(TARGET_TAG)) {
            logger.info("Senaryo @ResetAppBeforeTest tag'ine sahip. Uygulama yeniden yükleniyor...");
            AppiumDriver driver = DriverManager.getDriver();
            reinstallApp(driver);
        }

        if (scenario.getSourceTagNames().contains("@ClearCache")) {
            AppiumDriver driver = DriverManager.getDriver();
            clearCache(driver);
        }
    }

    /**
     * Her senaryodan SONRA çalışır.
     * Hata varsa ekran görüntüsü alır ve driver'ı kapatır.
     */
    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            logger.error("Senaryo BAŞARISIZ: {}", scenario.getName());

            AppiumDriver driver = DriverManager.getDriver();

            if (driver != null) {
                try {
                    byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Hata Ekran Görüntüsü - " + scenario.getName());
                    logger.info("Ekran görüntüsü alındı ve rapora eklendi.");

                } catch (Exception e) {
                    logger.error("Ekran görüntüsü alınırken hata oluştu!", e);
                }
            } else {
                logger.warn("Driver 'null' olduğu için ekran görüntüsü alınamadı.");
            }

        } else {
            logger.info("Senaryo BAŞARILI: {}", scenario.getName());
        }
        DriverManager.removeDriver();
    }


    private void reinstallApp(AppiumDriver driver) {
        if (driver == null) {
            logger.error("Driver null geldi, reinstall yapılamadı!");
            return;
        }

        if (driver instanceof AndroidDriver) {
            reinstallAppOnAndroid((AndroidDriver) driver);
        } else if (driver instanceof IOSDriver) {
            reinstallAppOnIOS((IOSDriver) driver);
        }
    }

    private void reinstallAppOnAndroid(AndroidDriver androidDriver) {
        try {
            if (androidDriver.isAppInstalled(APP_PACKAGE)) {
                androidDriver.removeApp(APP_PACKAGE);
            }
            androidDriver.installApp(APP_PATH);
            androidDriver.activateApp(APP_PACKAGE);
            logger.info("Android uygulama yeniden yüklendi. [Thread: {}]", Thread.currentThread().getId());
        } catch (Exception e) {
            logger.error("Android uygulama yeniden yüklenirken hata!", e);
            throw new RuntimeException("Android uygulama yeniden yüklenemedi", e);
        }
    }

    private void reinstallAppOnIOS(IOSDriver iosDriver) {
        try {
            if (iosDriver.isAppInstalled(APP_PACKAGE)) {
                iosDriver.removeApp(APP_PACKAGE);
            }
            iosDriver.installApp(APP_PATH);
            iosDriver.activateApp(APP_PACKAGE);
            logger.info("iOS uygulama yeniden yüklendi. [Thread: {}]", Thread.currentThread().getId());
        } catch (Exception e) {
            logger.error("iOS uygulama yeniden yüklenirken hata!", e);
            throw new RuntimeException("iOS uygulama yeniden yüklenemedi", e);
        }
    }

    private void clearCache(AppiumDriver driver) {
        driver.executeScript("mobile: clearApp", ImmutableMap.of("appId", APP_PACKAGE));
        driver.executeScript("mobile: activateApp", ImmutableMap.of("appId", APP_PACKAGE));
    }
}