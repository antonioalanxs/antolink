package org.example.backend.dto

data class LinkDTO(val url: String) {
    init {
        require(url.isNotBlank()) { "URL must not be blank." }
    }
}