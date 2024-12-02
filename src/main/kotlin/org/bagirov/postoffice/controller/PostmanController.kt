package org.bagirov.postoffice.controller

import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.service.PostmanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/postman")
class PostmanController (
    private val postmanService: PostmanService
){
    @GetMapping("/{id}")
    fun getPostman(@PathVariable id: UUID): ResponseEntity<PostmanEntity> {
        return ResponseEntity.ok(postmanService.getById(id))
    }

    @GetMapping()
    fun getAll() = ResponseEntity.ok(postmanService.getAll())

    @PostMapping()
    fun save(@RequestBody postman: PostmanEntity): ResponseEntity<PostmanEntity> {
        return ResponseEntity.ok(postmanService.save(postman))
    }
}