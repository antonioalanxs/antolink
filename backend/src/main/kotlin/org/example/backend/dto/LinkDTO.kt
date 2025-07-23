package org.example.backend.dto

import jakarta.validation.constraints.NotBlank
import org.example.backend.model.Link

/**
 * Represents a data transfer object for a link.
 *
 * @property url The URL of the link.
 * @property shortCode The short code of the link.
 * @property usageCount The usage count of the link. This field is only used in the response.
 */
data class LinkDTO(
    @field:NotBlank(message = "URL must not be blank")
    var url: String,

    @field:NotBlank(message = "Link Short code must not be blank")
    val shortCode: String,

    val usageCount: Int? = null
) {
    /**
     * Converts the [LinkDTO] to a [Link].
     *
     * @return The [Link] representation of the [LinkDTO].
     */
    fun toLink(): Link = Link(
        url = this.url,
        shortCode = this.shortCode
    )
}
