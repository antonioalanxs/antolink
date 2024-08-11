package org.example.backend.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.example.backend.model.AuthorizationCode

/**
 * Repository for handling [AuthorizationCode] entities.
 */
interface AuthorizationCodeRepository : MongoRepository<AuthorizationCode, String> {
    fun findByCode(code: String): AuthorizationCode?
}