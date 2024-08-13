package org.example.backend.controller.rest

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.example.backend.dto.LinkDTO
import org.example.backend.model.Link
import org.example.backend.response.Response
import org.example.backend.service.LinkService
import org.example.backend.annotation.RequireAuthorizationCode


/**
 * REST controller for handling [Link] entities related requests.
 *
 * @property linkService The service for handling [Link] entities.
 */
@RestController
@RequestMapping("/links")
class LinkController(private val linkService: LinkService) {
    /**
     * Creates a new [Link] based on the provided [LinkDTO].
     *
     * @param linkDTO The [LinkDTO] to create a new [Link].
     *
     * @return The created [Link], a conflict status if the [Link] already exists or a 401 status if the Authorization code provided in header is not correct.
     */
    @PostMapping
    @RequireAuthorizationCode
    fun create(@Valid @RequestBody linkDTO: LinkDTO): ResponseEntity<Any> {
        val link = this.linkService.create(linkDTO)
        return ResponseEntity(link, HttpStatus.CREATED)
    }

    /**
     * Redirects to the URL associated with the provided short code.
     *
     * @param shortCode The short code to redirect to.
     *
     * @return A [ResponseEntity] with a [Link] if the short code exists, otherwise a 404 status.
     */
    @GetMapping("/{shortCode}")
    fun redirect(@PathVariable shortCode: String): ResponseEntity<Link> {
        val url = this.linkService.redirect(shortCode)

        url?.let { return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", it).build() }
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    /**
     * Retrieves all [Link] entities in [LinkDTO]s.
     *
     * @return A list of all [Link] entities in [LinkDTO]s.
     */
    @GetMapping
    fun findAll(): ResponseEntity<List<LinkDTO>> {
        val links = this.linkService.findAll()
        return ResponseEntity(links, HttpStatus.OK)
    }
}