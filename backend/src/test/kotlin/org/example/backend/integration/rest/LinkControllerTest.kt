package org.example.backend.integration.rest

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers
import com.fasterxml.jackson.databind.ObjectMapper
import org.example.backend.dto.LinkDTO


@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class LinkControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    private val endpoint = "/links"

    private val linkDTO = LinkDTO(
        url = "https://www.youtube.com/",
        shortCode = "YouTube"
    )

    @Value("\${custom.authorization-code-header}")
    private lateinit var authorizationCodeHeader: String
    @Value("\${custom.authorization-code}")
    private lateinit var authorizationCode: String

    companion object {
        private val mongoDBContainer = MongoDBContainer("mongo:5.0")

        @JvmStatic
        @BeforeAll
        fun setUp() {
            mongoDBContainer.start()
            System.setProperty("spring.data.mongodb.uri", mongoDBContainer.replicaSetUrl)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            mongoDBContainer.stop()
        }
    }

    @Test
    fun `create link using correct Authorization code should return 201`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(this.linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, this.authorizationCode)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `create link using incorrect Authorization code should return 401`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(this.linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, "")
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}