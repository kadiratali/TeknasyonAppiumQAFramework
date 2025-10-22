package driver;

import config.AppSettings;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe driver manager that uses Factory Pattern to create
 * platform-specific driver instances.
 * Author: Cem AÇAR
 * Email: cem.acar03@gmail.com
 */
public class DriverManager {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    /**
     * O anki thread'e ait AppiumDriver'ı döndürür.
     * @return AppiumDriver
     */
    public static AppiumDriver getDriver() {
        return driver.get();
    }

    /**
     * Platform'a göre uygun factory'yi seçer ve driver'ı oluşturur.
     */
    public static void initializeDriver() {
        String platformName = AppSettings.getPlatform();

        logger.info("Platform: {} için driver başlatılıyor...", platformName);

        DriverFactory factory = getDriverFactory(platformName);
        AppiumDriver driverInstance = factory.createDriver();

        driver.set(driverInstance);
        logger.info("Driver başarıyla başlatıldı. Thread: {}", Thread.currentThread().getId());
    }

    /**
     * Platform'a göre uygun DriverFactory implementation'ını döndürür.
     * @param platform Platform adı (Android/iOS)
     * @return DriverFactory implementation
     */
    private static DriverFactory getDriverFactory(String platform) {
        if (platform.equalsIgnoreCase("Android")) {
            logger.debug("AndroidDriverFactory seçildi");
            return new AndroidDriverFactory();
        } else if (platform.equalsIgnoreCase("iOS")) {
            logger.debug("IOSDriverFactory seçildi");
            return new IOSDriverFactory();
        } else {
            throw new IllegalArgumentException("Desteklenmeyen platform: " + platform);
        }
    }

    /**
     * O anki thread'e ait driver'ı sonlandırır ve ThreadLocal'den kaldırır.
     */
    public static void removeDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                logger.info("Driver sonlandırıldı. Thread: {}", Thread.currentThread().getId());
            } catch (Exception e) {
                logger.error("Driver sonlandırılırken hata oluştu!", e);
            } finally {
                driver.remove();
            }
        }
    }
}