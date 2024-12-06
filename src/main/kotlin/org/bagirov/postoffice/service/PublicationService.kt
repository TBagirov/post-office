package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.PublicationRequest
import org.bagirov.postoffice.dto.request.update.PublicationUpdateRequest
import org.bagirov.postoffice.dto.response.PublicationResponse
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.PublicationTypeEntity
import org.bagirov.postoffice.repository.PublicationRepository
import org.bagirov.postoffice.repository.PublicationTypeRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PublicationService(
    private val publicationRepository: PublicationRepository,
    private val publicationTypeRepository: PublicationTypeRepository
) {

    fun getById(id: UUID): PublicationResponse =  publicationRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<PublicationResponse> =  publicationRepository.findAll().map { it -> it.convertToResponseDto() }

    @Transactional
    fun save(publication: PublicationRequest) : PublicationResponse {

        var tempPublicationType: PublicationTypeEntity? = publicationTypeRepository
            .findByType(publication.publicationType)

        if(tempPublicationType == null) {
            tempPublicationType = publicationTypeRepository.savePublicationType(
                id = UUID.randomUUID(),
                name = publication.publicationType
            )
        }

        val publicationSave: PublicationEntity = publicationRepository.savePublication(
            id = UUID.randomUUID(),
            index = publication.index,
            title = publication.title,
            publicationTypeId = tempPublicationType.id!!,
            price = publication.price
        )

        tempPublicationType.publications?.add(publicationSave)

        return publicationSave.convertToResponseDto()
    }

    @Transactional
    fun update(publication: PublicationUpdateRequest) : PublicationResponse {

        // Найти существующее издание
        val existingPublication = publicationRepository.findById(publication.id)
            .orElseThrow { IllegalArgumentException("Publication with ID ${publication.id} not found") }

        val publicationUpdate: PublicationEntity = publicationRepository.updatePublication(
            id = publication.id,
            index = publication.index,
            title = publication.title,
            publicationTypeId = publication.publicationTypeId,
            price = publication.price
        )

        existingPublication.index = publication.index
        existingPublication.title = publication.title
        existingPublication.price = publication.price

        return publicationUpdate.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): PublicationEntity {

        // Найти существующее издание
        val existingPublication = publicationRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Publication with ID ${id} not found") }

        // Удалить издание
        publicationRepository.deleteById(id)

        return existingPublication
    }

}
