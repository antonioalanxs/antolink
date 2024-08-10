package org.example.backend.exception

import org.springframework.http.HttpStatus

/**
 * Represents an error response.
 *
 * @property status The status of the response.
 * @property error The error message.
 *
 * @constructor Creates a new error response parsing the status of the response.
 */
data class ErrorResponse(val status: String, val error: String? = "An error occurred.") {
    constructor(status: HttpStatus, error: String?) : this(status.toString(), error)
}