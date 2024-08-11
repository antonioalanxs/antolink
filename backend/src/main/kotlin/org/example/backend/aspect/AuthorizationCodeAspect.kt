package org.example.backend.aspect

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.stereotype.Component
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import io.github.cdimascio.dotenv.dotenv
import org.aspectj.lang.annotation.Before
import org.example.backend.exception.AuthorizationCodeException


/**
 * Aspect to check the authorization code for methods annotated with `@RequireAuthorizationCode`.
 */
@Aspect
@Component
class AuthorizationCodeAspect {
    private val environment = dotenv()

    @Pointcut("@annotation(org.example.backend.annotation.RequireAuthorizationCode)")

    fun requireAuthorizationCode() {}

    /**
     * Checks the authorization code for methods annotated with `@RequireAuthorizationCode`.
     *
     * @throws AuthorizationCodeException If the authorization code is invalid.
     */
    @Before("requireAuthorizationCode()")
    fun checkAuthorizationCode() {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request

        val authorizationCode = request.getHeader("Authorization-Code")

        if (!isCorrectAuthorizationCode(authorizationCode))
            throw AuthorizationCodeException("Invalid Authorization code")
    }

    /**
     * Checks if the provided authorization code is correct.
     *
     * @param authorizationCode The authorization code to check.
     *
     * @return True if the authorization code is correct, false otherwise.
     */
    private fun isCorrectAuthorizationCode(authorizationCode: String): Boolean =
        authorizationCode == this.environment["AUTHORIZATION_CODE"]
}