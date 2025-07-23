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
     * Retrieves the URL associated with the provided short code and increments the usage count.
     *
     * @param shortCode The short code to retrieve the URL for.
     *
     * @return The URL associated with the short code if it exists, otherwise `null`.
     */
    fun redirect(shortCode: String): String? {
        var Link = this.linkRepository.findByShortCode(shortCode)

        if (Link == null) return null

        Link.usageCount++
        Link = this.linkRepository.save(Link)

        return Link.url
    }

    /**
     * Retrieves all [Link] entities in [LinkDTO]s.
     *
     * @return A list of all [Link] entities in [LinkDTO]s.
     */
    fun findAll(): List<LinkDTO> = this.linkRepository.findAll().map { it.toLinkDTO() }
}