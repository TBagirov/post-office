package org.bagirov.postoffice.controller


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.dto.response.RegionResponse
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.service.RegionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RestController
@RequestMapping("/api/region")
@Tag(name = "RegionController", description = "Контроллер для взаимодействия с регионами")
class RegionController(
    val regionService: RegionService
) {
    @GetMapping("/{id}")
    @Operation(
        summary = "Получение региона по id",
        description = "Получение данных региона по id"
    )
    fun getRegion(@PathVariable id: UUID): ResponseEntity<RegionResponse> {
        return ResponseEntity.ok(regionService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех регионов",
        description = "Получение всех данных о регионах"
    )
    fun getAll():ResponseEntity<List<RegionResponse>> = ResponseEntity.ok(regionService.getAll())

    @PostMapping()
    @Operation(
        summary = "Добавление региона",
        description = "Добавление данных региона"
    )
    fun save(@RequestBody region: RegionEntity): ResponseEntity<RegionResponse> {
        return ResponseEntity.ok(regionService.save(region))
    }

    @PutMapping()
    @Operation(
        summary = "Редактирование региона по id",
        description = "Редактирование данных региона по id"
    )
    fun update(@RequestBody district: RegionEntity): ResponseEntity<RegionResponse> {
        return ResponseEntity.ok(regionService.update(district))
    }

    @DeleteMapping()
    @Operation(
        summary = "Получение всех отношений",
        description = "Удаление региона по id, " +
                "удаленный регион в записях других таблиц изменится на null"
    )
    fun delete(@RequestParam id: UUID): ResponseEntity<RegionEntity> {
        return ResponseEntity.ok(regionService.delete(id))
    }


}