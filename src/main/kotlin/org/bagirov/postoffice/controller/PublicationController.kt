package org.bagirov.postoffice.controller


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.dto.request.PublicationRequest
import org.bagirov.postoffice.dto.request.update.PublicationUpdateRequest
import org.bagirov.postoffice.dto.response.PublicationResponse
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.service.PublicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RestController
@RequestMapping("/api/publication")
@Tag(name = "PublicationController", description = "Контроллер для взаимодействия с изданиями")
class PublicationController(
    val publicationService: PublicationService
) {
    @GetMapping("/{id}")
    @Operation(
        summary = "Получение издания по id",
        description = "Получение данных издания по id"
    )
    fun getPublication(@PathVariable id: UUID): ResponseEntity<PublicationResponse> {
        return ResponseEntity.ok(publicationService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех изданий",
        description = "Получение данных о всех изданиях"
    )
    fun getAll():ResponseEntity<List<PublicationResponse>> = ResponseEntity.ok(publicationService.getAll())

    @PostMapping()
    @Operation(
        summary = "Добавление издания",
        description = "Добавление данных об издании"
    )
    fun save(@RequestBody publication: PublicationRequest): ResponseEntity<PublicationResponse> {
        return ResponseEntity.ok(publicationService.save(publication))
    }

    @PutMapping()
    @Operation(
        summary = "Редактирование издания по id",
        description = "Редактирование данных издания по id"
    )
    fun update(@RequestBody publication: PublicationUpdateRequest): ResponseEntity<PublicationResponse> {
        return ResponseEntity.ok(publicationService.update(publication))
    }

    @DeleteMapping()
    @Operation(
        summary = "Удаление издания по id",
        description = "Удаление издания по id, " +
                "удаленное издание в записях других таблиц изменится на null"
    )
    fun delete(@RequestParam id: UUID): ResponseEntity<PublicationEntity> {
        return ResponseEntity.ok(publicationService.delete(id))
    }
}