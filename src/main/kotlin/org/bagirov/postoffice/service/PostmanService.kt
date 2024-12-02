package org.bagirov.postoffice.service


import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.repository.PostmanRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PostmanService(
    private val postmanRepository: PostmanRepository
) {

    fun getById(id: UUID): PostmanEntity = postmanRepository.findById(id).orElse(null)

    fun getAll():MutableList<PostmanEntity> = postmanRepository.findAll()

    @Transactional
    fun save(postman: PostmanEntity):PostmanEntity = postmanRepository.save(postman)
}
