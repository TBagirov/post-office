package org.bagirov.postoffice.controller


import org.bagirov.postoffice.dto.request.update.DistrictUpdateRequest
import org.bagirov.postoffice.dto.response.DistrictResponse
import org.bagirov.postoffice.dto.response.RegionResponse
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.service.RegionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/region")
class RegionController(
    val regionService: RegionService
) {
    @GetMapping("/{id}")
    fun getRegion(@PathVariable id: UUID): ResponseEntity<RegionResponse> {
        return ResponseEntity.ok(regionService.getById(id))
    }

    @GetMapping()
    fun getAll():ResponseEntity<List<RegionResponse>> = ResponseEntity.ok(regionService.getAll())

    @PostMapping()
    fun save(@RequestBody region: RegionEntity): ResponseEntity<RegionResponse> {
        return ResponseEntity.ok(regionService.save(region))
    }

    @PutMapping()
    fun update(@RequestBody district: RegionEntity): ResponseEntity<RegionResponse> {
        return ResponseEntity.ok(regionService.update(district))
    }

    @DeleteMapping()
    fun delete(@RequestParam id: UUID): ResponseEntity<RegionEntity> {
        return ResponseEntity.ok(regionService.delete(id))
    }


}