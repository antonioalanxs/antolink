package org.example.backend.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Link(
    @Id val id: String? = null, val url: String, val shortCode: String
) {
    /**
     * Constructor for creating a new [Link] with a random short code.
     */
    constructor(url: String) : this(
        url = url, shortCode = url.hashCode().toString().take(6)
    )
}