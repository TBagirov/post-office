package org.bagirov.postoffice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.dto.request.PublicationTypeRequest
import org.bagirov.postoffice.dto.response.PublicationTypeResponse
import org.bagirov.postoffice.entity.PublicationTypeEntity
import org.bagirov.postoffice.service.PublicationTypeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3001"))
@RestController
@RequestMapping("/api/publication-type")
@Tag(name = "PublicationTypeController", description = "Контроллер для взаимодействия с типом изданий")
class PublicationTypeController(
    val publicationTypeService: PublicationTypeService
) {
    @GetMapping("/{id}")
    @Operation(
        summary = "Получение типа издания по id",
        description = "Получение данных типа издания по id"
    )
    fun getPublicationType(@PathVariable id: UUID): ResponseEntity<PublicationTypeResponse> {
        return ResponseEntity.ok(publicationTypeService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех типов издания",
        description = "Получение данных о всех типах издания"
    )
    fun getAll():ResponseEntity<List<PublicationTypeResponse>> = ResponseEntity.ok(publicationTypeService.getAll())

    @PostMapping()
    @Operation(
        summary = "Добавление типа издания",
        description = "Добавление данных о типе издания"
    )
    fun save(@RequestBody publicationType: PublicationTypeRequest): ResponseEntity<PublicationTypeResponse> {
        return ResponseEntity.ok(publicationTypeService.save(publicationType))
    }

    @PutMapping()
    @Operation(
        summary = "Редактирование издания по id",
        description = "Редактирование данных издания по id"
    )
    fun update(@RequestBody publicationType: PublicationTypeEntity): ResponseEntity<PublicationTypeResponse> {
        return ResponseEntity.ok(publicationTypeService.update(publicationType))
    }

    @DeleteMapping()
    @Operation(
        summary = "Получение всех отношений",
        description = "Удаление типа издания по id, " +
                "удаленный тип издания в записях других таблиц изменится на null"
    )
    fun delete(@RequestParam id: UUID): ResponseEntity<PublicationTypeEntity> {
        return ResponseEntity.ok(publicationTypeService.delete(id))
    }

}