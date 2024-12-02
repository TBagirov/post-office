package org.bagirov.postoffice.controller

import org.bagirov.postoffice.dto.request.PublicationTypeRequest
import org.bagirov.postoffice.dto.response.PublicationTypeResponse
import org.bagirov.postoffice.service.PublicationTypeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/publication-type")
class PublicationTypeController(
    val publicationTypeService: PublicationTypeService
) {
    @GetMapping("/{id}")
    fun getPublicationType(@PathVariable id: UUID): ResponseEntity<PublicationTypeResponse> {
        return ResponseEntity.ok(publicationTypeService.getById(id))
    }

    @GetMapping()
    fun getAll():ResponseEntity<List<PublicationTypeResponse>> = ResponseEntity.ok(publicationTypeService.getAll())

    @PostMapping()
    fun save(@RequestBody publicationType: PublicationTypeRequest): ResponseEntity<PublicationTypeResponse> {
        return ResponseEntity.ok(publicationTypeService.save(publicationType))
    }
}