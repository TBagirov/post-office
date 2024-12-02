package org.bagirov.postoffice.controller


import org.bagirov.postoffice.dto.request.SubscriberRequest
import org.bagirov.postoffice.dto.response.SubscriberResponse
import org.bagirov.postoffice.service.SubscriberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/subscriber")
class SubscriberController(
    val subscriberService: SubscriberService
) {
    @GetMapping("/{id}")
    fun getDistrict(@PathVariable id: UUID): ResponseEntity<SubscriberResponse> {
        return ResponseEntity.ok(subscriberService.getById(id))
    }

    @GetMapping()
    fun getAll():ResponseEntity<List<SubscriberResponse>> = ResponseEntity.ok(subscriberService.getAll())

    @PostMapping()
    fun save(@RequestBody subscriber: SubscriberRequest): ResponseEntity<SubscriberResponse> {
        return ResponseEntity.ok(subscriberService.save(subscriber))
    }

}