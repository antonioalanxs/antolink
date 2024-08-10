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

@RestController
@RequestMapping("/links")
class LinkController(private val linkService: LinkService) {
    /**
     * Creates a new [Link] based on the provided [LinkDTO].
     *
     * @param linkDTO The [LinkDTO] to create a new [Link].
     *
     * @return The created [Link] or a conflict status if the [Link] already exists.
     */
    @PostMapping
    fun create(@Valid @RequestBody linkDTO: LinkDTO): ResponseEntity<Any> {
        val link = this.linkService.create(linkDTO)

        return link?.let { ResponseEntity.ok(it) } ?: ResponseEntity(
            Response(
                HttpStatus.CONFLICT,
                mapOf("message" to "Link already exists")
            ),
            HttpStatus.CONFLICT
        )
    }

    /**
     * Retrieves a [Link] based on the provided short code.
     *
     * @param shortCode The short code to retrieve the [Link].
     *
     * @return The [Link] or a not found status if the [Link] does not exist.
     */
    @GetMapping("/{shortCode}")
    fun redirect(@PathVariable shortCode: String): ResponseEntity<Link> {
        val url = this.linkService.read(shortCode)?.url

        url?.let { return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", it).build() }
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}