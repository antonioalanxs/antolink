package org.example.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest


/**
 * Handles exceptions globally by catching [Exception] and returning a [ResponseEntity] with a [ErrorResponse].
 */
@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        exception: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errors = exception.bindingResult.fieldErrors
            .associate { it.field to it.defaultMessage }

        val body = ErrorResponse(
            HttpStatus.BAD_REQUEST,
            errors
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}
