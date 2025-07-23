package org.example.backend.annotation

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION

/**
 * Custom annotation to require authorization code verification.
 */
@Target(FUNCTION)
@Retention(RUNTIME)
annotation class RequireAuthorizationCode
