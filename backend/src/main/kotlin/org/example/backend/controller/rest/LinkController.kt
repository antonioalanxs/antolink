package org.example.backend.controller.rest

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
import org.example.backend.service.LinkService

@RestController
@RequestMapping("/links")
class LinkController(private val linkService: LinkService) {
    /**
     * Creates a new [Link] based on the provided [LinkDTO].
     */
    @PostMapping
    fun create(@RequestBody linkDTO: LinkDTO): ResponseEntity<Link> {
        val link = linkService.create(linkDTO)
        return ResponseEntity(link, HttpStatus.CREATED)
    }

    /**
     * Retrieves a [Link] based on the provided short code.
     */
    @GetMapping("/{shortCode}")
    fun redirect(@PathVariable shortCode: String): ResponseEntity<Link> {
        val url = linkService.read(shortCode)?.url

        url?.let { return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", it).build() }
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
    }
}