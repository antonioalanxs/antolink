package org.example.backend.exception

import org.springframework.http.HttpStatus

/**
 * Represents an message response.
 *
 * @property status The status of the response.
 * @property errors Optional map of field errors.
 *
 * @constructor Creates a new message response parsing the status of the response.
 */
data class ErrorResponse(
    val status: String,
    val errors: Map<String, String?>
) {
    constructor(status: HttpStatus, errors: Map<String, String?>) : this(
        status.toString(),
        errors
    )
}
