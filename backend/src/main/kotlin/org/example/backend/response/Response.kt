package org.example.backend.response

import org.springframework.http.HttpStatus

/**
 * Represents a message response.
 *
 * @property status The status of the response.
 * @property detail More specific information about the response.
 */
data class Response(
    val status: HttpStatus,
    val detail: Map<String, String?> = emptyMap()
) {
    constructor(status: HttpStatus, message: String?) : this(
        status,
        mapOf("message" to message)
    )
}
