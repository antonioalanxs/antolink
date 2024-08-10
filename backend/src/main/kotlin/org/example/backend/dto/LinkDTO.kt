package org.example.backend.dto

import jakarta.validation.constraints.NotBlank

data class LinkDTO(
    @field:NotBlank(message = "URL must not be blank")
    val url: String
)
