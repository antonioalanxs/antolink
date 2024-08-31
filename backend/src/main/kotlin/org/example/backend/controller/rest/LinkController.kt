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
import org.example.backend.service.LinkService
import org.example.backend.annotation.RequireAuthorizationCode
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.parameters.RequestBody as SwaggerRequestBody


/**
 * REST controller for handling [Link] entities related requests.
 *
 * @property linkService The service for handling [Link] entities.
 */
@RestController
@RequestMapping("/links")
class LinkController(private val linkService: LinkService) {

    /**
     * Creates a new [Link] entity based on the provided [LinkDTO].
     *
     * @param linkDTO The [LinkDTO] to create a new [Link] entity from.
     *
     * @return A [ResponseEntity] with the created [Link] entity.
     */
    @Operation(summary = "Creates a new Link", description = "Creates a new Link based on the provided LinkDTO. Requires an Authorization code header to be present in the request.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201", description = "Link created successfully", content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LinkDTO::class),
                        examples = [
                            ExampleObject(
                                name = "Link Created Response Example",
                                summary = "Example of a successful link creation",
                                value = """
                        {
                            "id": "66d1949cb4ea9d04d75f3f7a",
                            "url": "https://www.youtube.com/",
                            "shortCode": "YouTube",
                            "usageCount": 0
                        }
                        """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "409", description = "Link already exists", content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "Conflict Response Example",
                                summary = "Example of a conflict response",
                                value = """
                        {
                            "status": "CONFLICT",
                            "detail": {
                                "message": "{ url: \"https://x.com/\" } already exists"
                            }
                        }
                        """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401", description = "Invalid Authorization code header", content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "Invalid Authorization code header Response Example",
                                summary = "Example of an Invalid Authorization code header response",
                                value = """
                        {
                            "status": "UNAUTHORIZED",
                            "detail": {
                                "message": "Invalid Authorization code header"
                            }
                        }
                        """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    @PostMapping
    @RequireAuthorizationCode
    fun create(
        @SwaggerRequestBody(
            description = "The LinkDTO object that needs to be created",
            required = true,
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = LinkDTO::class),
                examples = [
                    ExampleObject(
                        name = "LinkDTO Example",
                        summary = "Example of a LinkDTO",
                        value = """
                        {
                            "url": "https://www.youtube.com/",
                            "shortCode": "YouTube"
                        }
                        """
                    )
                ]
            )]
        ) @Valid @RequestBody linkDTO: LinkDTO
    ): ResponseEntity<Any> {
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
    @Operation(
        summary = "Redirects to the URL associated with the provided short code",
        description = "Redirects to the URL associated with the provided short code."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "301", description = "Redirected successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Void::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Link not found",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = Void::class))]
            )
        ]
    )
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
    @Operation(summary = "Retrieve a list of all Links", description = "Retrieves a list of all Links.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Links retrieved successfully", content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LinkDTO::class),
                        examples = [
                            ExampleObject(
                                name = "Links Retrieved Response Example",
                                summary = "Example of a successful links retrieval",
                                value = """
                        [
                            {
                                "url": "https://nova.elportaldelalumno.com/",
                                "shortCode": "nova",
                                "usageCount": 0
                            },
                            {
                                "url": "https://x.com/",
                                "shortCode": "x",
                                "usageCount": 0
                            },
                            {
                                "url": "https://www.stremio.com/",
                                "shortCode": "stremio",
                                "usageCount": 0
                            },
                            {
                                "url": "https://www.youtube.com/",
                                "shortCode": "YouTube",
                                "usageCount": 0
                            }
                        ]
                        """
                            )
                        ]
                    )
                ]
            )
        ]
    )
    @GetMapping
    fun findAll(): ResponseEntity<List<LinkDTO>> {
        val links = this.linkService.findAll()
        return ResponseEntity(links, HttpStatus.OK)
    }
}