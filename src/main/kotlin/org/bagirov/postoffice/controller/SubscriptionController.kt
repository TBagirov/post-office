package org.bagirov.postoffice.controller


import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.bagirov.postoffice.dto.request.SubscriptionRequest
import org.bagirov.postoffice.dto.request.update.SubscriptionUpdateRequest
import org.bagirov.postoffice.dto.response.SubscriptionResponse
import org.bagirov.postoffice.service.SubscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RestController
@RequestMapping("/api/subscription")
@Tag(name = "SubscriptionController", description = "Контроллер для взаимодействия с подписками подписчиков на печатные издания")
class SubscriptionController(
    private val subscriptionService: SubscriptionService
) {
    @GetMapping("/{id}")
    @Operation(
        summary = "Получение подписки по id",
        description = "Получение данных подписки по id"
    )
    fun getDistrict(@PathVariable id: UUID):ResponseEntity<SubscriptionResponse>{
        return ResponseEntity.ok(subscriptionService.getById(id))
    }

    @GetMapping()
    @Operation(
        summary = "Получение всех подписок",
        description = "Получение данных о всех подписках"
    )
    fun getAll(): ResponseEntity<List<SubscriptionResponse>> = ResponseEntity.ok(subscriptionService.getAll())

    @PostMapping()
    @Operation(
        summary = "Добавление подписки",
        description = "Добавление данных о подписке"
    )
    fun save(@RequestBody subscription: SubscriptionRequest): ResponseEntity<SubscriptionResponse> {
        return ResponseEntity.ok(subscriptionService.save(subscription))
    }

    @PutMapping()
    @Operation(
        summary = "Редактирование подписки по id",
        description = "Редактирование данных подписки по id"
    )
    fun update(@RequestBody publication: SubscriptionUpdateRequest): ResponseEntity<SubscriptionResponse> {
        return ResponseEntity.ok(subscriptionService.update(publication))
    }

    @DeleteMapping()
    @Operation(
        summary = "Удаление подписки по id",
        description = "Удаление подписки по id, " +
                "удаленная подписка в записях других таблиц изменится на null"
    )
    fun delete(@RequestParam id: UUID): ResponseEntity<SubscriptionResponse> {
        return ResponseEntity.ok(subscriptionService.delete(id))
    }

}