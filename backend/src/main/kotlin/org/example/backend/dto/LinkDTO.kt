package org.example.backend.dto

import jakarta.validation.constraints.NotBlank

/**
 * Represents a data transfer object for a link.
 *
 * @property url The URL of the link.
 */
data class LinkDTO(
    @field:NotBlank(message = "URL must not be blank")
    val url: String,
)
