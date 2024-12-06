package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.response.PostmanResponse
import org.bagirov.postoffice.dto.response.RegionResponse
import org.bagirov.postoffice.entity.DistrictEntity
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.repository.PostmanRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PostmanService(
    private val postmanRepository: PostmanRepository
) {

    fun getById(id: UUID): PostmanResponse = postmanRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<PostmanResponse> = postmanRepository.findAll().map { it -> it.convertToResponseDto() }

    @Transactional
    fun save(postman: PostmanEntity):PostmanResponse {

        val postmanSave: PostmanEntity = postmanRepository.savePostman(
            id =  postman.id ?: UUID.randomUUID(),
            name = postman.name,
            surname = postman.surname,
            patronymic = postman.patronymic
        )

        return postmanSave.convertToResponseDto()
    }

    @Transactional
    fun update(postman: PostmanEntity): PostmanResponse {

        // Найти существующего почтальона
        val existingPostman = postmanRepository.findById(postman.id!!)
            .orElseThrow { IllegalArgumentException("Postman with ID ${postman.id} not found") }

        // Выполнить обновление в базе данных
        val postmanUpdate = postmanRepository.updatePostman(
            id = postman.id!!,
            name = postman.name,
            surname = postman.surname,
            patronymic = postman.patronymic,
        )

        existingPostman.name = postman.name
        existingPostman.surname = postman.surname
        existingPostman.patronymic = postman.patronymic

        return postmanUpdate.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): PostmanEntity {
        // Найти существующего почтальона
        val existingPostman = postmanRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Postman with ID ${id} not found") }

        // Удалить отношение почтальонов к районам
        postmanRepository.deleteById(id)

        // Преобразовать удалённое отношение почтальонов к районам в DTO и вернуть
        return existingPostman
    }

}
