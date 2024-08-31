package org.example.backend.service

import org.springframework.stereotype.Service
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

/**
 * Service for Selenium related operations.
 */
@Service
class SeleniumService {

    /**
     * Retrieves the final URL after following all (JavaScript) redirects.
     *
     * @param url The URL to retrieve the final URL from.
     */
    fun getFinalUrl(url: String): String {
        val webDriver = webDriver()

        return try {
            webDriver.get(url)
            webDriver.currentUrl
        } catch (exception: Exception) {
            throw IllegalArgumentException("Resource not found")
        } finally {
            webDriver.quit()
        }
    }

    /**
     * Creates a new [WebDriver] instance.
     *
     * @return The [WebDriver] instance.
     */
    private fun webDriver(): WebDriver {
        val options = ChromeOptions()

        options.addArguments("--headless")
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-dev-shm-usage")

        return ChromeDriver(options)
    }
}
