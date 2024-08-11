package org.example.backend.configuration

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.annotation.PreDestroy

/**
 * Configuration for Selenium.
 *
 * @property driver The [WebDriver] to use for Selenium operations.
 */
@Configuration
class SeleniumConfiguration {
    private lateinit var driver: WebDriver

    /**
     * Creates and configures a new [WebDriver] instance if it does not already exist.
     *
     * @return The [WebDriver] instance.
     */
    @Bean
    fun webDriver(): WebDriver {
        if (!::driver.isInitialized) {
            val options = ChromeOptions()

            options.addArguments("--headless")
            options.addArguments("--no-sandbox")
            options.addArguments("--disable-dev-shm-usage")

            driver = ChromeDriver(options)
        }

        return driver
    }

    /**
     * Cleans up the [WebDriver] instance when the application is shut down.
     */
    @PreDestroy
    fun cleanup() {
        if (::driver.isInitialized) driver.quit()
    }
}
