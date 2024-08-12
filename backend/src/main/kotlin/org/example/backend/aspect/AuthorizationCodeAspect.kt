package org.example.backend.aspect

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.stereotype.Component
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.annotation.Before
import org.example.backend.exception.AuthorizationCodeException
import org.springframework.beans.factory.annotation.Value


/**
 * Aspect to check the authorization code for methods annotated with `@RequireAuthorizationCode`.
 */
@Aspect
@Component
class AuthorizationCodeAspect {
    @Value("\${custom.authorization-code-header}")
    private lateinit var authorizationCodeHeader: String
    @Value("\${custom.authorization-code}")
    private lateinit var authorizationCode: String

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

        val authorizationCode = request.getHeader(this.authorizationCodeHeader)

        if (authorizationCode != this.authorizationCode)
            throw AuthorizationCodeException("Invalid Authorization code")
    }
}