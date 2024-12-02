package org.bagirov.postoffice.controller


import org.bagirov.postoffice.dto.request.PublicationRequest
import org.bagirov.postoffice.dto.response.PublicationResponse
import org.bagirov.postoffice.service.PublicationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/publication")
class PublicationController(
    val publicationService: PublicationService
) {
    @GetMapping("/{id}")
    fun getPublication(@PathVariable id: UUID): ResponseEntity<PublicationResponse> {
        return ResponseEntity.ok(publicationService.getById(id))
    }

    @GetMapping()
    fun getAll():ResponseEntity<List<PublicationResponse>> = ResponseEntity.ok(publicationService.getAll())

    @PostMapping()
    fun save(@RequestBody publication: PublicationRequest): ResponseEntity<PublicationResponse> {
        return ResponseEntity.ok(publicationService.save(publication))
    }
}