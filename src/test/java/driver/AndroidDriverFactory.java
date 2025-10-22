package driver;

import config.AppSettings;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;

/**
 * Factory implementation for creating Android-specific AppiumDriver instances.
 * Author: Kadir Atali
 */
public class AndroidDriverFactory implements DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(AndroidDriverFactory.class);

    @Override
    public AppiumDriver createDriver() {
        try {
            String appiumServerUrl = AppSettings.getAppiumUrl();
            JSONObject capabilitiesJson = AppSettings.getCapabilities();

            UiAutomator2Options options = new UiAutomator2Options();

            // JSON'daki tüm capability'leri options'a ata
            capabilitiesJson.forEach((key, value) -> {
                options.setCapability(key.toString(), value);
                logger.debug("Android Capability set: {} = {}", key, value);
            });

            logger.info("Android driver oluşturuluyor... Thread: {}", Thread.currentThread().getId());
            AppiumDriver driver = new AndroidDriver(new URL(appiumServerUrl), options);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

            logger.info("Android driver başarıyla oluşturuldu. Thread: {}", Thread.currentThread().getId());
            return driver;

        } catch (Exception e) {
            logger.error("Android driver oluşturulamadı!", e);
            throw new RuntimeException("Android driver oluşturulamadı: " + e.getMessage(), e);
        }
    }
}