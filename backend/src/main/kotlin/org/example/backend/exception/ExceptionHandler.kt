package org.example.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.example.backend.response.Response


/**
 * Handles exceptions globally by catching [Exception] and returning a [ResponseEntity] with a [Response].
 */
@ControllerAdvice
class ExceptionHandler {

    /**
     * [MethodArgumentNotValidException] handler.
     *
     * @param exception The [MethodArgumentNotValidException] to handle.
     *
     * @return A [ResponseEntity] with a [Response] containing the field errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        exception: MethodArgumentNotValidException
    ): ResponseEntity<Response> {
        val errors = exception.bindingResult.fieldErrors
            .associate { it.field to it.defaultMessage }

        val body = Response(
            HttpStatus.BAD_REQUEST,
            errors
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    /**
     * [IllegalArgumentException] and [HttpMessageNotReadableException] handler.
     *
     * @param exception The [Exception] to handle.
     *
     * @return A [ResponseEntity] with a [Response] containing the exception message.
     */
    @ExceptionHandler(IllegalArgumentException::class, HttpMessageNotReadableException::class)
    fun handleIllegalArgumentException(
        exception: Exception
    ): ResponseEntity<Response> {
        val body = Response(
            HttpStatus.BAD_REQUEST,
            exception.message
        )

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }
}
