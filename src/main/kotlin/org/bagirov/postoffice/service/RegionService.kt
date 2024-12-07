package org.bagirov.postoffice.service


import jakarta.persistence.EntityManager
import org.bagirov.postoffice.dto.request.update.DistrictUpdateRequest
import org.bagirov.postoffice.dto.response.DistrictResponse
import org.bagirov.postoffice.dto.response.RegionResponse
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.repository.RegionRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class RegionService(
    private val regionRepository: RegionRepository
) {

    fun getById(id: UUID): RegionResponse = regionRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<RegionResponse> = regionRepository.findAll().map{it -> it.convertToResponseDto()}

    @Transactional
    fun save(region: RegionEntity):RegionResponse{


        val regionSave: RegionEntity = regionRepository.saveRegion(
            id =  region.id ?: UUID.randomUUID(),
            name = region.name
        )

       return regionSave.convertToResponseDto()
    }

    @Transactional
    fun update(region: RegionEntity): RegionResponse {

        // Найти существующий регион
        val existingRegion = regionRepository.findById(region.id!!)
            .orElseThrow { IllegalArgumentException("Region with ID ${region.id} not found") }

        // Выполнить обновление в базе данных
        val regionUpdate = regionRepository.updateRegion(
            id = region.id!!,
            name = region.name,
        )

        existingRegion.name = region.name

        return regionUpdate.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): RegionEntity {
        // Найти существующий регион
        val existingRegion = regionRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Region with ID ${id} not found") }

        // Удалить регион
        regionRepository.deleteById(id)

        return existingRegion
    }

}
