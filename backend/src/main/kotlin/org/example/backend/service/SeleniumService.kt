package org.example.backend.service

import org.openqa.selenium.WebDriver
import org.springframework.stereotype.Service

/**
 * Service for Selenium related operations.
 *
 * @param webDriver The [WebDriver] to use for Selenium operations.
 */
@Service
class SeleniumService(private val webDriver: WebDriver) {
    /**
     * Retrieves the final URL after following all (JavaScript) redirects.
     *
     * @param url The URL to retrieve the final URL from.
     */
    fun getFinalUrl(url: String): String {
        return try {
            this.webDriver.get(url)
            this.webDriver.currentUrl
        } catch (exception: Exception) {
            throw IllegalArgumentException("Resource not found")
        }
    }
}
