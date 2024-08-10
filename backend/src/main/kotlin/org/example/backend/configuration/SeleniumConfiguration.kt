package org.example.backend.configuration

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.annotation.PreDestroy

@Configuration
class SeleniumConfiguration {
    private lateinit var driver: WebDriver

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

    @PreDestroy
    fun cleanup() {
        if (::driver.isInitialized) driver.quit()
    }
}
