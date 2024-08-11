package org.example.backend.model

import jakarta.validation.constraints.NotBlank
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.index.Indexed

/**
 * Represents a link entity.
 *
 * @property id The unique identifier of the link.
 * @property url The URL of the link.
 * @property shortCode The short code of the link.
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
    val shortCode: String
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
}