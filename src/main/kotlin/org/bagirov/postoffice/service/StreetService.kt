package org.bagirov.postoffice.service

import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.update.DistrictUpdateRequest
import org.bagirov.postoffice.dto.request.update.StreetUpdateRequest
import org.bagirov.postoffice.dto.response.DistrictResponse
import org.bagirov.postoffice.dto.response.StreetResponse
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.bagirov.postoffice.repository.RegionRepository
import org.bagirov.postoffice.repository.StreetRepository
import org.bagirov.postoffice.utill.convertToEntity
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StreetService(
    private val streetRepository: StreetRepository,
    private val regionRepository: RegionRepository
) {

    fun getById(id: UUID): StreetResponse = streetRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<StreetResponse> = streetRepository.findAll().map { it -> it.convertToResponseDto() }

    @Transactional
    fun save(streetRequest: StreetRequest): StreetResponse {


        val streetEntity = streetRequest.convertToEntity()

        val temp: RegionEntity = findNearestRegion(streetEntity.name)

        streetEntity.region = temp


        val streetSave: StreetEntity = streetRepository.saveStreet(
            id = streetEntity.id ?: UUID.randomUUID(),
            name = streetEntity.name,
            regionId = streetEntity.region!!.id!!
        )

        temp.streets?.add(streetSave)


        return streetSave.convertToResponseDto()
    }

    @Transactional
    fun update(street: StreetUpdateRequest): StreetResponse {

        // Найти существующую улицу
        val existingStreet = streetRepository.findById(street.id!!)
            .orElseThrow { IllegalArgumentException("Street with ID ${street.id} not found") }

        val tempRegion: RegionEntity? = regionRepository.findById(street.regionId).orElse(null)


        // Выполнить обновление в базе данных
        val streetUpdate = streetRepository.updateStreet(
            id = street.id,
            regionId = tempRegion!!.id!!,
            name = street.name
        )

        // Обновить связи
        existingStreet.region = tempRegion
        existingStreet.name = street.name

        return streetUpdate.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): StreetResponse {

        // Найти существующую улицу
        val existingStreet = streetRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Street with ID ${id} not found") }

        // Удалить улицу
        streetRepository.deleteById(id)

        return existingStreet.convertToResponseDto()
    }


    private fun findNearestRegion(streetName: String): RegionEntity {
        val regions = regionRepository.findAll()

        if(regions.isEmpty()) throw ChangeSetPersister.NotFoundException()

        for(region in regions){

            if(region.streets == null) continue

            if(region.streets!!.any { street -> street.name == streetName }){
                return region
            }
        }

        return regions.random()
    }
}
