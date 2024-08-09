package org.example.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class BackendApplication

@Document
data class Link(
    @Id
    val id: String? = null,
    val url: String,
    val shortCode: String
) {
    /**
     * Constructor for creating a new [Link] with a random short code.
     */
    constructor(url: String) : this(
        url = url,
        shortCode = url.hashCode().toString().take(6)
    )
}

interface LinkRepository : MongoRepository<Link, String> {
    fun findByShortCode(shortCode: String): Link?
}

data class LinkDTO(val url: String) {
    init {
        require(url.isNotBlank()) { "URL must not be blank." }
    }
}

@Service
class LinkService(private val linkRepository: LinkRepository) {
    fun create(linkDTO: LinkDTO): Link {
        val link = Link(linkDTO.url)
        return linkRepository.save(link)
    }

    fun read(shortCode: String): Link {
        return linkRepository.findByShortCode(shortCode)
            ?: throw IllegalArgumentException("Link not found.")
    }
}

@RestController
@RequestMapping("/api/links")
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
    fun read(@PathVariable shortCode: String): ResponseEntity<Link> {
        val link = linkService.read(shortCode)
        return ResponseEntity(link, HttpStatus.OK)
    }
}

fun main(args: Array<String>) {
    runApplication<BackendApplication>(*args)
}
