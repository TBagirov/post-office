package org.bagirov.postoffice.controller


import org.bagirov.postoffice.dto.request.SubscriptionRequest
import org.bagirov.postoffice.dto.response.SubscriptionResponse
import org.bagirov.postoffice.service.SubscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/subscription")
class SubscriptionController(
    private val subscriptionService: SubscriptionService
) {
    @GetMapping("/{id}")
    fun getDistrict(@PathVariable id: UUID):ResponseEntity<SubscriptionResponse>{
        return ResponseEntity.ok(subscriptionService.getById(id))
    }

    @GetMapping()
    fun getAll(): ResponseEntity<List<SubscriptionResponse>> = ResponseEntity.ok(subscriptionService.getAll())

    @PostMapping()
    fun save(@RequestBody subscription: SubscriptionRequest): ResponseEntity<SubscriptionResponse> {
        return ResponseEntity.ok(subscriptionService.save(subscription))
    }
}