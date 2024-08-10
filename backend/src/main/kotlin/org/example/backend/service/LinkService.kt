package org.example.backend.service

import org.springframework.stereotype.Service
import org.example.backend.model.Link
import org.example.backend.dto.LinkDTO
import org.example.backend.repository.LinkRepository

@Service
class LinkService(private val linkRepository: LinkRepository) {
    fun create(linkDTO: LinkDTO): Link {
        val link = Link(linkDTO.url)
        return linkRepository.save(link)
    }

    fun read(shortCode: String): Link? = linkRepository.findByShortCode(shortCode)
}