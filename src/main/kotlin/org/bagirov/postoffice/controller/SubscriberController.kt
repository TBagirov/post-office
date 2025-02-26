package org.bagirov.postoffice.controller


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
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

    private val log = KotlinLogging.logger {}

    @GetMapping("/{id}")
    @Operation(
        summary = "Получение подписчика по id",
        description = "Получение данных о подписчике по id"
    )
    fun getSubscriber(@PathVariable id: UUID): ResponseEntity<SubscriberResponse> {
        log.info { "Request Subscriber by id: $id" }
        return ResponseEntity.ok(subscriberService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех подписчиков",
        description = "Получение данных всех подписчиков"
    )
    fun getAll():ResponseEntity<List<SubscriberResponse>> = ResponseEntity.ok(subscriberService.getAll())

    @PostMapping("/create")
    @Operation(
        summary = "Добавление подписчиков",
        description = "Добавление данных о подписчике"
    )
    fun save(@RequestHeader("Authorization")token: String,
             @RequestBody subscriber: SubscriberRequest): ResponseEntity<SubscriberResponse> {
        log.info { "Request create Subscriber" }
        return ResponseEntity.ok(subscriberService.save(token.substring(7), subscriber))
    }

    @PutMapping("/update")
    @Operation(
        summary = "Редактирование подписчика по id",
        description = "Редактирование данных подписчика по id"
    )
    fun update(@RequestHeader("Authorization") token: String, @RequestBody subscriber: SubscriberUpdateRequest): ResponseEntity<SubscriberResponse> {
        log.info { "Request update Subscriber" }
        return ResponseEntity.ok(subscriberService.update(token.substring(7), subscriber))
    }

    @DeleteMapping("/delete")
    @Operation(
        summary = "Удаление подписчика по id",
        description = "Удаление подписчика по id, " +
                "удаленный подписчик в записях других таблиц изменится на null"
    )
    fun delete(@RequestHeader("Authorization")token: String): ResponseEntity<SubscriberResponse> {
        log.info { "Request delete Subscriber" }
        return ResponseEntity.ok(subscriberService.delete(token.substring(7)))
    }
}