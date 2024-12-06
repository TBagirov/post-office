package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.PublicationTypeRequest
import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.update.StreetUpdateRequest
import org.bagirov.postoffice.dto.response.PublicationTypeResponse
import org.bagirov.postoffice.dto.response.StreetResponse
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.PublicationTypeEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.bagirov.postoffice.repository.PublicationTypeRepository
import org.bagirov.postoffice.utill.convertToEntity
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PublicationTypeService(
    private val publicationTypeRepository: PublicationTypeRepository
) {

    fun getById(id: UUID): PublicationTypeResponse = publicationTypeRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<PublicationTypeResponse> = publicationTypeRepository.findAll().map { it -> it.convertToResponseDto() }


    @Transactional
    fun save(publicationType: PublicationTypeRequest): PublicationTypeResponse {



        val publicationTypeSave: PublicationTypeEntity = publicationTypeRepository.savePublicationType(
            id = UUID.randomUUID(),
            name = publicationType.type
        )

        return publicationTypeSave.convertToResponseDto()
    }

    @Transactional
    fun update(publicationType: PublicationTypeEntity): PublicationTypeResponse {

        // Найти существующий тип издания
        val existingPublicationType = publicationTypeRepository.findById(publicationType.id!!)
            .orElseThrow { IllegalArgumentException("Publication Type with ID ${publicationType.id} not found") }

        val publicationTypeUpdate = publicationTypeRepository.updatePublicationType(
            id = publicationType.id!!,
            name = publicationType.type,
        )

        existingPublicationType.type = publicationType.type

        return publicationTypeUpdate.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): PublicationTypeEntity {

        // Найти существующий тип издания
        val existingPublicationType = publicationTypeRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Publication Type with ID ${id} not found") }

        // Удалить тип издания
        publicationTypeRepository.deleteById(id)

        return existingPublicationType
    }

}
