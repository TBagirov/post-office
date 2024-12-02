package org.bagirov.postoffice.service

import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.response.StreetResponse
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
    fun save(streetRequest: StreetRequest, flagEntity:Boolean = false): StreetResponse {

        val streetEntity = streetRequest.convertToEntity()

        val temp: RegionEntity = findNearestRegion(streetEntity.name)

        streetEntity.region = temp

        val streetSave: StreetEntity = streetRepository.save(streetEntity)

        temp?.streets?.add(streetSave)


        return streetSave.convertToResponseDto()
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
