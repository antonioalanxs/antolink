package org.example.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Represents a link entity.
 *
 * @property id The unique identifier of the link.
 * @property url The URL of the link.
 * @property shortCode The short code of the link.
 */
@Document
data class Link(
    @Id
    val id: String? = null,
    val url: String,
    val shortCode: String
) {
    constructor(url: String) : this(
        url = url, shortCode = url.hashCode().toString().take(6)
    )
}