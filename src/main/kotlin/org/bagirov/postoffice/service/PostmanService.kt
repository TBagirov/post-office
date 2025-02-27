package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.auth.RegistrationRequest
import org.bagirov.postoffice.dto.response.PostmanResponse
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.UserEntity
import org.bagirov.postoffice.repository.PostmanRepository
import org.bagirov.postoffice.repository.RoleRepository
import org.bagirov.postoffice.repository.UserRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import kotlin.NoSuchElementException

@Service
class PostmanService(
    private val postmanRepository: PostmanRepository,
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun getById(id: UUID): PostmanResponse = postmanRepository.findById(id)
        .orElseThrow{ NoSuchElementException("Postman with ID ${id} not found") }
        .convertToResponseDto()

    fun getAll():List<PostmanResponse> = postmanRepository.findAll().map { it.convertToResponseDto() }

    @Transactional
    fun save(request: RegistrationRequest):PostmanResponse {

        val users = userRepository.findAll()

        users.forEach { user ->
            if (request.username == user.username) {
                throw IllegalArgumentException("Пользователь с таким username уже существует")
            }
        }

        val rolePostman = roleRepository
            .findByName("POSTMAN")

        val user = UserEntity (
            username = request.username,
            password = passwordEncoder.encode(request.password),
            role = rolePostman!!,
            name = request.name,
            surname = request.surname,
            patronymic =request.patronymic,
            email = request.email,
            phone = request.phone,
            createdAt = LocalDateTime.now()
        )

        val savedUser = userRepository.save(user)
        val postman = PostmanEntity(
            user = savedUser
        )

        val savedPostman = postmanRepository.save(postman)
        return savedPostman.convertToResponseDto()
    }

    @Transactional
    fun update(postman: PostmanEntity): PostmanResponse {

        // Найти существующего почтальона
        val existingPostman = postmanRepository.findById(postman.id!!)
            .orElseThrow { NoSuchElementException("Postman with ID ${postman.id} not found") }


       postmanRepository.save(existingPostman)


        return existingPostman.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): PostmanResponse {
        // Найти существующего почтальона
        val existingPostman = postmanRepository.findById(id)
            .orElseThrow { NoSuchElementException("Postman with ID ${id} not found") }

        // Отключаем почтальона от пользователя перед удалением
        existingPostman.user.postman = null
        userRepository.save(existingPostman.user) // Сохраняем обновление

        // Удалить отношение почтальонов к районам
        postmanRepository.deleteById(id)

        // Преобразовать удалённое отношение почтальонов к районам в DTO и вернуть
        return existingPostman.convertToResponseDto()
    }

}
