package driver;

import config.AppSettings;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;

/**
 * Factory implementation for creating iOS-specific AppiumDriver instances.
 * Author: Kadir Atali
 */
public class IOSDriverFactory implements DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(IOSDriverFactory.class);

    @Override
    public AppiumDriver createDriver() {
        try {
            String appiumServerUrl = AppSettings.getAppiumUrl();
            JSONObject capabilitiesJson = AppSettings.getCapabilities();

            XCUITestOptions options = new XCUITestOptions();

            // JSON'daki tüm capability'leri options'a ata
            capabilitiesJson.forEach((key, value) -> {
                options.setCapability(key.toString(), value);
                logger.debug("iOS Capability set: {} = {}", key, value);
            });

            logger.info("iOS driver oluşturuluyor... Thread: {}", Thread.currentThread().getId());
            AppiumDriver driver = new IOSDriver(new URL(appiumServerUrl), options);

            // Varsayılan bekleme süresi
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

            logger.info("iOS driver başarıyla oluşturuldu. Thread: {}", Thread.currentThread().getId());
            return driver;

        } catch (Exception e) {
            logger.error("iOS driver oluşturulamadı!", e);
            throw new RuntimeException("iOS driver oluşturulamadı: " + e.getMessage(), e);
        }
    }
}