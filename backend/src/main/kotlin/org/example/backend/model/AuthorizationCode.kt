package org.example.backend.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id

/**
 * Represents an authorization code entity.
 *
 * This code will be needed to shorten a link.
 *
 * @property id The unique identifier of the link.
 * @property code The authorization code.
 */
@Document
data class AuthorizationCode(
    @Id val id: String? = null, val code: String
)