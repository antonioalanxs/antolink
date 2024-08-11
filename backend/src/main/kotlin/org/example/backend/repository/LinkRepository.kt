package org.example.backend.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.example.backend.model.Link

/**
 * Repository for handling [Link] entities.
 */
interface LinkRepository : MongoRepository<Link, String> {
    fun findByShortCode(shortCode: String): Link?
    fun findByUrl(url: String): Link?
}