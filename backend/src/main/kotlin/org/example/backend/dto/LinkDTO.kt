package org.example.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class LinkDTO(
    @field:NotBlank(message = "URL cannot be blank")
    @field:Pattern(
        regexp = "^(https?|ftp):\\/\\/[^\\s/\$.?#].[^\\s]*\$\n",
        message = "Invalid URL"
    )
    val url: String
)
