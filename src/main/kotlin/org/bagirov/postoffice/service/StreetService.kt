package org.bagirov.postoffice.service

import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.update.StreetUpdateRequest
import org.bagirov.postoffice.dto.response.StreetResponse
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.repository.RegionRepository
import org.bagirov.postoffice.repository.StreetRepository
import org.bagirov.postoffice.utill.convertToEntity
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StreetService(
    private val streetRepository: StreetRepository,
    private val regionRepository: RegionRepository,
    private val regionService: RegionService
) {

    fun getById(id: UUID): StreetResponse =
        streetRepository.findById(id)
            .orElseThrow { NoSuchElementException("Street with ID ${id} not found") }
            .convertToResponseDto()

    fun getAll(): List<StreetResponse> =
        streetRepository.findAll().map { it.convertToResponseDto() }

    @Transactional
    fun save(streetRequest: StreetRequest): StreetResponse {

        val streetEntity = streetRequest.convertToEntity()
        val nearestRegion: RegionEntity = findNearestRegion(streetEntity.name)

        streetEntity.region = nearestRegion

        val streetSave = streetRepository.save(streetEntity)

        nearestRegion.streets?.add(streetSave)

        return streetSave.convertToResponseDto()
    }

    @Transactional
    fun update(streetRequest: StreetUpdateRequest): StreetResponse {

        // Найти существующую улицу
        val existingStreet = streetRepository.findById(streetRequest.id)
            .orElseThrow { NoSuchElementException("Street with ID ${streetRequest.id} not found") }

        val newRegion: RegionEntity? = regionRepository.findById(streetRequest.regionId).orElse(null)

        // Выполнить обновление в базе данных
        existingStreet.apply {
            region = newRegion
            name = streetRequest.name
        }

        val streetUpdate = streetRepository.save(existingStreet)

        // Обновить связи
        newRegion?.streets?.add(streetUpdate)

        return streetUpdate.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): StreetResponse {

        // Найти существующую улицу
        val existingStreet = streetRepository.findById(id)
            .orElseThrow { NoSuchElementException("Street with ID ${id} not found") }

        // Удалить улицу
        streetRepository.delete(existingStreet)

        return existingStreet.convertToResponseDto()
    }


    private fun findNearestRegion(streetName: String): RegionEntity {
        val regions = regionRepository.findAll()

        if (regions.isEmpty()) {
            return regionService.saveEnt(RegionEntity(name = "Region1"))
        }


        return regions.firstOrNull { region ->
            region.streets?.any { it.name == streetName } ?: false
        } ?: regions.random()
    }
}
