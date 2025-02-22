package org.bagirov.postoffice.controller

import org.bagirov.postoffice.dto.response.RoleResponse
import org.bagirov.postoffice.service.RoleService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/role")
class RoleController(
    private val roleService: RoleService
) {

    @GetMapping("/{id}")
    fun getRole(@PathVariable id: UUID): ResponseEntity<RoleResponse> {
        return ResponseEntity.ok(roleService.getById(id))
    }

    @GetMapping()
    fun getAll(): ResponseEntity<List<RoleResponse>>{
        return ResponseEntity.ok(roleService.getAll())
    }


    @PostMapping()
    fun save(@RequestBody role: String): ResponseEntity<RoleResponse> {
        return ResponseEntity.ok(roleService.save(role))
    }


    @DeleteMapping()
    fun delete(@RequestParam id: UUID): ResponseEntity<RoleResponse> {
        return ResponseEntity.ok(roleService.delete(id))
    }

}