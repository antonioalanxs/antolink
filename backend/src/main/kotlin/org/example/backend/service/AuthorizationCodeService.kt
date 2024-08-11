package org.example.backend.service

import org.example.backend.model.AuthorizationCode
import org.example.backend.repository.AuthorizationCodeRepository

/**
 * Service for handling [AuthorizationCode] entities.
 *
 * @property authorizationCodeRepository The repository for [AuthorizationCode] entities.
 */
class AuthorizationCodeService(
    private val authorizationCodeRepository: AuthorizationCodeRepository
) {
}