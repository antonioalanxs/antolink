package org.example.backend.integration.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.context.ActiveProfiles
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers


/**
 * Base class for integration tests with all the necessary utilities.
 *
 * @property mockMvc The mock MVC instance for performing HTTP requests.
 * @property objectMapper The object mapper instance for serializing and deserializing objects.
 * @property authorizationCodeHeader The authorization code header for creating links.
 * @property authorizationCode The authorization code for creating links.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
abstract class BaseIntegrationTest {
    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected val objectMapper = ObjectMapper()

    @Value("\${custom.authorization-code-header}")
    protected lateinit var authorizationCodeHeader: String

    @Value("\${custom.authorization-code}")
    protected lateinit var authorizationCode: String

    companion object {
        /**
         * The MongoDB container for running integration tests.
         */
        protected val mongoDBContainer = MongoDBContainer("mongo:5.0")

        /**
         * Starts the MongoDB container before all tests.
         */
        @JvmStatic
        @BeforeAll
        fun setUp() {
            mongoDBContainer.start()
            System.setProperty("spring.data.mongodb.uri", mongoDBContainer.replicaSetUrl)
        }

        /**
         * Stops the MongoDB container after all tests.
         */
        @JvmStatic
        @AfterAll
        fun tearDown() {
            mongoDBContainer.stop()
        }
    }
}