package driver;

import io.appium.java_client.AppiumDriver;

/**
 * The {@code DriverFactory} interface defines a contract for creating
 * platform-specific {@code AppiumDriver} instances.
 * Implementations of this interface should provide platform-specific
 * configuration and initialization for Appium drivers.
 */
public interface DriverFactory {

    /**
     * Creates and returns an instance of {@code AppiumDriver} configured
     * for the target platform.
     *
     * @return A configured {@code AppiumDriver} instance.
     */
    AppiumDriver createDriver();
}