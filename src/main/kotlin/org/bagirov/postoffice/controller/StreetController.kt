package org.bagirov.postoffice.controller


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.update.StreetUpdateRequest
import org.bagirov.postoffice.dto.response.StreetResponse
import org.bagirov.postoffice.service.StreetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RestController
@RequestMapping("/api/street")
@Tag(name = "StreetController", description = "Контроллер для взаимодействия с улицами")
class StreetController(
    private val streetService: StreetService
) {
    @GetMapping("/{id}")
    @Operation(
        summary = "Получение улицы по id",
        description = "Получение данных о улице по id"
    )
    fun getStreet(@PathVariable id: UUID):ResponseEntity<StreetResponse>{
        return ResponseEntity.ok(streetService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех улиц",
        description = "Получение данных о всех улицах"
    )
    fun getAll():ResponseEntity<List<StreetResponse>> =
        ResponseEntity.ok(streetService.getAll())

    @PostMapping()
    @Operation(
        summary = "Добавление улицы",
        description = "Добавление данных улицы, " +
                "улица определяется в какой-то регион автоматически"
    )
    fun save(@RequestBody streetRequest: StreetRequest): ResponseEntity<StreetResponse> {
        return ResponseEntity.ok(streetService.save(streetRequest))
    }

    @PutMapping()
    @Operation(
        summary = "Редактирование улицы по id",
        description = "Редактирование данных улицы по id"
    )
    fun update(@RequestBody streetUpdate: StreetUpdateRequest): ResponseEntity<StreetResponse> {
        return ResponseEntity.ok(streetService.update(streetUpdate))
    }

    @DeleteMapping()
    @Operation(
        summary = "Удаление улицы по id",
        description = "Удаление улицы по id, " +
                "удаленная улица в записях других таблиц изменится на null"
    )
    fun delete(@RequestParam id: UUID): ResponseEntity<StreetResponse> {
        return ResponseEntity.ok(streetService.delete(id))
    }
}