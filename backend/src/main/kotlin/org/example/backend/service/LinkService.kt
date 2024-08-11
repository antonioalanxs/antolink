package org.example.backend.service

import org.springframework.stereotype.Service
import org.example.backend.model.Link
import org.example.backend.dto.LinkDTO
import org.example.backend.repository.LinkRepository

/**
 * Service for handling [Link] entities.
 *
 * @property linkRepository The repository for [Link] entities.
 * @property seleniumService The service for handling Selenium WebDriver operations.
 */
@Service
class LinkService(
    private val linkRepository: LinkRepository,
    private val seleniumService: SeleniumService
) {
    /**
     * Creates a new [Link] entity from the provided [LinkDTO] if it does not already exist.
     *
     * This function uses the [SeleniumService] to resolve the final URL to handle redirects or dynamic content.
     *
     * @param linkDTO The data transfer object containing the URL to be processed.
     *
     * @return The created [Link] entity if successful, or null if a [Link] with the URL already exists.
     */
    fun create(linkDTO: LinkDTO): Link? {
        var url = linkDTO.url

        url = this.seleniumService.getFinalUrl(url)

        val existingLink = this.linkRepository.findByUrl(url)

        if (existingLink != null) return null

        val link = Link(url)
        return this.linkRepository.save(link)
    }

    /**
     * Find a [Link] entity by its short code.
     *
     * @param shortCode The short code to search for.
     */
    fun read(shortCode: String): Link? = this.linkRepository.findByShortCode(shortCode)
}