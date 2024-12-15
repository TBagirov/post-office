package org.bagirov.postoffice.controller


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.dto.request.SubscriberRequest
import org.bagirov.postoffice.dto.request.update.SubscriberUpdateRequest
import org.bagirov.postoffice.dto.response.SubscriberResponse
import org.bagirov.postoffice.service.SubscriberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RestController
@RequestMapping("/api/subscriber")
@Tag(name = "SubscriberController", description = "Контроллер для взаимодействия с подписчиками на печатные издания")
class SubscriberController(
    val subscriberService: SubscriberService
) {
    @GetMapping("/{id}")
    @Operation(
        summary = "Получение подписчика по id",
        description = "Получение данных о подписчике по id"
    )
    fun getDistrict(@PathVariable id: UUID): ResponseEntity<SubscriberResponse> {
        return ResponseEntity.ok(subscriberService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех подписчиков",
        description = "Получение данных всех подписчиков"
    )
    fun getAll():ResponseEntity<List<SubscriberResponse>> = ResponseEntity.ok(subscriberService.getAll())

    @PostMapping()
    @Operation(
        summary = "Добавление подписчиков",
        description = "Добавление данных о подписчике"
    )
    fun save(@RequestBody subscriber: SubscriberRequest): ResponseEntity<SubscriberResponse> {
        return ResponseEntity.ok(subscriberService.save(subscriber))
    }

    @PutMapping()
    @Operation(
        summary = "Редактирование подписчика по id",
        description = "Редактирование данных подписчика по id"
    )
    fun update(@RequestBody publication: SubscriberUpdateRequest): ResponseEntity<SubscriberResponse> {
        return ResponseEntity.ok(subscriberService.update(publication))
    }

    @DeleteMapping()
    @Operation(
        summary = "Удаление подписчика по id",
        description = "Удаление подписчика по id, " +
                "удаленный подписчик в записях других таблиц изменится на null"
    )
    fun delete(@RequestParam id: UUID): ResponseEntity<SubscriberResponse> {
        return ResponseEntity.ok(subscriberService.delete(id))
    }
}