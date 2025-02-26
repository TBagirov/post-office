package org.bagirov.postoffice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.bagirov.postoffice.dto.auth.RegistrationRequest
import org.bagirov.postoffice.dto.response.PostmanResponse
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.service.PostmanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RestController
@RequestMapping("/api/postman")
@Tag(name = "PostmanController", description = "Контроллер для взаимодействия с почтальонами")
class PostmanController (
    private val postmanService: PostmanService
){

    private val log = KotlinLogging.logger {}

    @GetMapping("/{id}")
    @Operation(
        summary = "Получение почтальона по id",
        description = "Получение данных о почтальона по id"
    )
    fun getPostman(@PathVariable id: UUID): ResponseEntity<PostmanResponse> {
        log.info { "Request Postman by id: $id" }
        return ResponseEntity.ok(postmanService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех почтальонов",
        description = "Получение данных о всех почтальонах"
    )
    fun getAll():ResponseEntity<List<PostmanResponse>> {
        log.info { "Request all Postman" }
        return ResponseEntity.ok(postmanService.getAll())
    }

    @PostMapping()
    @Operation(
        summary = "Добавление почтальона",
        description = "Добавление данных о почтальоне"
    )
    fun save(@RequestBody postman: RegistrationRequest): ResponseEntity<PostmanResponse> {
        log.info { "Request create postman" }
        return ResponseEntity.ok(postmanService.save(postman))
    }

    @PutMapping()
    @Operation(
        summary = "Редактирование почтальона по id",
        description = "Редактирование данных о почтальоне по id"
    )
    fun update(@RequestBody postman: PostmanEntity): ResponseEntity<PostmanResponse> {
        log.info { "Request update postman by id: ${postman.id}" }
        return ResponseEntity.ok(postmanService.update(postman))
    }

    @DeleteMapping()
    @Operation(
        summary = "Удаление почтальона по id",
        description = "Удаление почтальона по id, " +
                "удаленный почтальон в записях других таблиц изменится на null"
    )
    fun delete(@RequestParam id: UUID): ResponseEntity<PostmanResponse> {
        log.info { "Request delete postman by id: $id" }
        return ResponseEntity.ok(postmanService.delete(id))
    }

}