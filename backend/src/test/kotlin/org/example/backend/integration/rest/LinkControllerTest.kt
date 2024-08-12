package org.example.backend.integration.rest

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.junit.jupiter.api.Test
import org.example.backend.dto.LinkDTO

/**
 * Integration tests for the link controller.
 *
 * @property endpoint The endpoint for the link controller.
 * @property linkDTO The link DTO for creating links.
 */
class LinkControllerTest: BaseIntegrationTest() {
    private val endpoint = "/links"

    private val linkDTO = LinkDTO(
        url = "https://www.youtube.com/",
        shortCode = "YouTube"
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
    fun `create link using incorrect Authorization code should return 401`() {
        this.mockMvc.perform(
            MockMvcRequestBuilders.post(this.endpoint)
                .content(this.objectMapper.writeValueAsString(this.linkDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .header(this.authorizationCodeHeader, "")
        ).andExpect(MockMvcResultMatchers.status().isUnauthorized)
    }
}