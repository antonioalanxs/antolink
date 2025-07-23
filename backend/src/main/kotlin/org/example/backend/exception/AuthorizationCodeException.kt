package org.example.backend.exception

/**
 * Custom exception for invalid authorization codes needed to create links.
 */
class AuthorizationCodeException(message: String) : RuntimeException(message)