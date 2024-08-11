package org.example.backend.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import io.github.cdimascio.dotenv.dotenv
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
    private val environment = dotenv()

    /**
     * Runs the seeder.
     */
    @PostConstruct
    fun run() {
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

    @PreDestroy
    fun destroy() {
        if (
            !this.environment["DESTROY_DATABASE_ON_EXIT"].toBoolean()
        ) return

        this.linkRepository.deleteAll()
    }
}