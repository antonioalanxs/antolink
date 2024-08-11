package org.example.backend.service

import org.springframework.stereotype.Service
import org.example.backend.model.Link
import org.example.backend.dto.LinkDTO
import org.example.backend.exception.CustomDuplicateKeyException
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
     * Creates a new [Link] entity from the provided [LinkDTO].
     *
     * This function uses the [SeleniumService] to resolve the final URL to handle redirects or dynamic content.
     *
     * @param linkDTO The [LinkDTO] to create the [Link] entity from.
     *
     * @return The created [Link] entity if successful or throws an [IllegalArgumentException].
     */
    fun create(linkDTO: LinkDTO): Link {
        linkDTO.url = this.seleniumService.getFinalUrl(linkDTO.url)

        return try {
            this.linkRepository.save(linkDTO.toLink())
        } catch (exception: Exception) {
            throw CustomDuplicateKeyException(exception.message)
        }
    }

    /**
     * Find a [Link] entity by its short code.
     *
     * @param shortCode The short code to search for.
     */
    fun read(shortCode: String): Link? = this.linkRepository.findByShortCode(shortCode)
}