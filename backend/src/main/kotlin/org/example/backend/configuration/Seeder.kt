package org.example.backend.configuration

import org.springframework.core.io.ClassPathResource
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.beans.factory.annotation.Value
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
     *
     * @return A list of created [Link] entities.
     */
    private fun link(): List<Link>? {
        if (!isRepositoryEmpty(this.linkRepository)) return null

        return this.linkRepository.saveAll(
            loadFromJSON("data/links.json")
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

    /**
     * Loads entities from a JSON file.
     *
     * @param path The path to the JSON file.
     */
    private inline fun <reified T> loadFromJSON(path: String): List<T> {
        val resource = ClassPathResource(path)
        val file = resource.file
        val mapper = jacksonObjectMapper()
        return mapper.readValue(file)
    }
}