package org.bagirov.postoffice.controller

import org.bagirov.postoffice.dto.request.DistrictRequest
import org.bagirov.postoffice.dto.request.update.DistrictUpdateRequest
import org.bagirov.postoffice.dto.response.DistrictResponse
import org.bagirov.postoffice.entity.DistrictEntity
import org.bagirov.postoffice.service.DistrictService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/district")
class DistrictController(
    private val districtService: DistrictService
) {

    @GetMapping("/{id}")
    fun getDistrict(@PathVariable id: UUID):ResponseEntity<DistrictResponse>{
        return ResponseEntity.ok(districtService.getById(id))
    }

    @GetMapping()
    fun getAll():ResponseEntity<List<DistrictResponse>> = ResponseEntity.ok(districtService.getAll())

    @PostMapping()
    fun save(@RequestBody district: DistrictRequest): ResponseEntity<DistrictResponse> {
        return ResponseEntity.ok(districtService.save(district))
    }

    @PutMapping()
    fun update(@RequestBody district: DistrictUpdateRequest): ResponseEntity<DistrictResponse> {
        return ResponseEntity.ok(districtService.update(district))
    }

    @DeleteMapping()
    fun delete(@RequestParam id: UUID): ResponseEntity<DistrictResponse> {
        return ResponseEntity.ok(districtService.delete(id))
    }

}