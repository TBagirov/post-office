package org.bagirov.postoffice.controller


import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.update.StreetUpdateRequest
import org.bagirov.postoffice.dto.response.RegionResponse
import org.bagirov.postoffice.dto.response.StreetResponse
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.bagirov.postoffice.service.StreetService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api/street")
class StreetController(
    private val streetService: StreetService
) {
    @GetMapping("/{id}")
    fun getStreet(@PathVariable id: UUID):ResponseEntity<StreetResponse>{
        return ResponseEntity.ok(streetService.getById(id))
    }

    @GetMapping()
    fun getAll():ResponseEntity<List<StreetResponse>> =
        ResponseEntity.ok(streetService.getAll())

    @PostMapping()
    fun save(@RequestBody streetRequest: StreetRequest): ResponseEntity<StreetResponse> {
        return ResponseEntity.ok(streetService.save(streetRequest))
    }

    @PutMapping()
    fun update(@RequestBody streetUpdate: StreetUpdateRequest): ResponseEntity<StreetResponse> {
        return ResponseEntity.ok(streetService.update(streetUpdate))
    }

    @DeleteMapping()
    fun delete(@RequestParam id: UUID): ResponseEntity<StreetResponse> {
        return ResponseEntity.ok(streetService.delete(id))
    }
}