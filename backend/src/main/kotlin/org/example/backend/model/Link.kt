package org.example.backend.model

import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed
import org.example.backend.dto.LinkDTO

/**
 * Represents a link entity.
 *
 * @property id The unique identifier of the link.
 * @property url The URL of the link.
 * @property shortCode The short code of the link.
 * @property usageCount The usage count of the link.
 */
@Document(collection = "links")
data class Link(
    @Id
    val id: String? = null,

    @NotBlank
    @Indexed(unique = true)
    val url: String,

    @NotBlank
    @Indexed(unique = true)
    val shortCode: String,

    var usageCount: Int = 0
) {
    /**
     * Constructor for creating a [Link] entity without an ID.
     *
     * @param url The URL of the link.
     * @param shortCode The short code of the link.
     */
    constructor(
        url: String,
        shortCode: String
    ) : this(null, url, shortCode)

    /**
     * Converts the [Link] entity to a [LinkDTO].
     *
     * @param usageCount The usage count of the link. This field is only used in the response.
     */
    fun toLinkDTO(): LinkDTO = LinkDTO(this.url, this.shortCode, this.usageCount)
}