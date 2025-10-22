package driver;

import config.AppSettings;
import io.appium.java_client.AppiumDriver;
import org.json.simple.JSONObject;
import org.openqa.selenium.MutableCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Abstract base class for driver factories.
 * Provides common functionality for all platform-specific factories.
 */
public abstract class BaseDriverFactory implements DriverFactory {

    protected static final Logger logger = LoggerFactory.getLogger(BaseDriverFactory.class);
    protected static final String APPIUM_SERVER_URL = "http://localhost:4723";
    protected static final int DEFAULT_IMPLICIT_WAIT = 15;

    @Override
    public final AppiumDriver createDriver() {
        try {
            logger.info("Creating {} driver for thread: {}",
                    getPlatformName(), Thread.currentThread().getId());

            URL serverUrl = getAppiumServerUrl();
            MutableCapabilities options = createPlatformOptions();

            validateCapabilities(options);

            AppiumDriver driver = initializeDriver(serverUrl, options);
            configureDriver(driver);

            logger.info("{} driver successfully created for thread: {}",
                    getPlatformName(), Thread.currentThread().getId());

            return driver;

        } catch (Exception e) {
            String errorMsg = String.format("Failed to create %s driver: %s",
                    getPlatformName(), e.getMessage());
            logger.error(errorMsg, e);
            throw new DriverInitializationException(errorMsg, e);
        }
    }

    /**
     * Creates platform-specific capabilities/options.
     *
     * @return Configured MutableCapabilities for the platform
     */
    protected abstract MutableCapabilities createPlatformOptions();

    /**
     * Initializes the actual driver instance.
     *
     * @param serverUrl Appium server URL
     * @param options Platform-specific options
     * @return Initialized AppiumDriver
     */
    protected abstract AppiumDriver initializeDriver(URL serverUrl, MutableCapabilities options);

    /**
     * Applies capabilities from AppSettings to the options object.
     *
     * @param options Options object to populate
     */
    protected void applyCapabilities(MutableCapabilities options) {
        JSONObject capabilities = AppSettings.getCapabilities();

        capabilities.forEach((key, value) -> {
            String capKey = key.toString();
            options.setCapability(capKey, value);
            logger.debug("Capability set: {} = {}", capKey, value);
        });
    }

    /**
     * Validates that required capabilities are present.
     *
     * @param options Options to validate
     * @throws DriverInitializationException if validation fails
     */
    protected void validateCapabilities(MutableCapabilities options) {
        // Platform-specific validation can be overridden
        if (options.getCapability("platformName") == null) {
            throw new DriverInitializationException("platformName capability is required");
        }
    }

    /**
     * Configures the driver after initialization (timeouts, etc.).
     *
     * @param driver Driver to configure
     */
    protected void configureDriver(AppiumDriver driver) {
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(DEFAULT_IMPLICIT_WAIT));

        logger.debug("Implicit wait set to {} seconds", DEFAULT_IMPLICIT_WAIT);
    }

    /**
     * Gets the Appium server URL from configuration.
     *
     * @return Appium server URL
     * @throws MalformedURLException if URL is invalid
     */
    protected URL getAppiumServerUrl() throws MalformedURLException {
        return new URL(APPIUM_SERVER_URL);
    }
}
