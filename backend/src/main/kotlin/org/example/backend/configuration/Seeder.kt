package org.example.backend.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.beans.factory.annotation.Value
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.example.backend.repository.LinkRepository
import org.example.backend.model.Link


/**
 * Service for seeding the database.
 *
 * @property linkRepository The repository for [Link] entities.
 */
@Configuration
class Seeder(
    private val linkRepository: LinkRepository
) {
    @Value("\${custom.initialize-database}")
    private var initialize: Boolean = false
    @Value("\${custom.destroy-database}")
    private var destroy: Boolean = false

    /**
     * Runs the seeder.
     */
    @PostConstruct
    fun run() {
        if (!this.initialize) return

        link()
    }

    /**
     * Creates [Link] entities.
     */
    private fun link(): List<Link>? {
        if (!isRepositoryEmpty(this.linkRepository)) return null

        return this.linkRepository.saveAll(
            listOf(
                Link("https://nova.elportaldelalumno.com/", "nova"),
                Link("https://x.com/", "x"),
                Link("https://www.stremio.com/", "stremio"),
            )
        )
    }

    /**
     * Checks if a repository is empty.
     *
     * @param repository The repository to check.
     */
    private fun isRepositoryEmpty(repository: MongoRepository<*, *>): Boolean =
        repository.count() == 0L

    /**
     * Destroys the database on exit.
     */
    @PreDestroy
    fun destroy() {
        if (!this.destroy) return

        this.linkRepository.deleteAll()
    }
}