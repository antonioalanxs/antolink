package org.example.backend.integration.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.http.MediaType
import org.junit.jupiter.api.Test
import org.example.backend.dto.LinkDTO
import org.example.backend.repository.LinkRepository


/**
 * Integration tests for the link controller.
 *
 * @property linkRepository The link repository.
 * @property endpoint The endpoint for the link controller.
 * @property linkDTO The link DTO for creating links.
 */
class LinkControllerTest : BaseIntegrationTest() {
    @Autowired
    private lateinit var linkRepository: LinkRepository

    private val endpoint = "/links"

    private val linkDTO = LinkDTO(
        url = "https://spring.io/guides/gs/testing-web",
        shortCode = "test"
    )

    @Test
    fun `create link using correct Authorization code should return 201`() {
        this.mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(this.linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, this.authorizationCode)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `create link using existing one should return 409`() {
        this.mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(this.linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, this.authorizationCode)
        ).andExpect(MockMvcResultMatchers.status().isConflict)
    }

    @Test
    fun `create link using incorrect Authorization code should return 401`() {
        this.mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(this.linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, "")
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }

    @Test
    fun `create link using invalid URL should return 400`() {
        this.mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(
                    this.objectMapper.writeValueAsString(
                        LinkDTO(
                            url = "test",
                            shortCode = "test"
                        )
                    )
                )
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, this.authorizationCode)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `create link whose final URL is the same as another link has should return 409`() {
        var linkDTO = LinkDTO(
            url = "https://nova.elportaldelalumno.com/",
            shortCode = "another"
        )

        this.mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, this.authorizationCode)
        ).andExpect(MockMvcResultMatchers.status().isCreated)

        linkDTO = LinkDTO(
            url = "https://nova.elportaldelalumno.com/Login",
            shortCode = "additional"
        )

        this.mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, this.authorizationCode)
        ).andExpect(MockMvcResultMatchers.status().isConflict)
    }
}