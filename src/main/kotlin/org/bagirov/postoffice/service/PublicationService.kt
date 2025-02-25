package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.PublicationRequest
import org.bagirov.postoffice.dto.request.PublicationTypeRequest
import org.bagirov.postoffice.dto.request.update.PublicationUpdateRequest
import org.bagirov.postoffice.dto.response.PublicationResponse
import org.bagirov.postoffice.dto.response.PublicationTypeResponse
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.PublicationTypeEntity
import org.bagirov.postoffice.repository.PublicationRepository
import org.bagirov.postoffice.repository.PublicationTypeRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PublicationService(
    private val publicationRepository: PublicationRepository,
    private val publicationTypeRepository: PublicationTypeRepository,
    private val publicationTypeService: PublicationTypeService
) {

    fun getById(id: UUID): PublicationResponse =  publicationRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<PublicationResponse> =  publicationRepository.findAll().map { it.convertToResponseDto() }

    @Transactional
    fun save(publication: PublicationRequest): PublicationResponse {
        var tempPublicationType = publicationTypeRepository.findByType(publication.publicationType)

        if (tempPublicationType == null) {
            tempPublicationType = PublicationTypeEntity (
                type = publication.publicationType
            )
            tempPublicationType = publicationTypeRepository.save(tempPublicationType) // Сохраняем сразу
        }


        val publicationNew = PublicationEntity(
            index = publication.index,
            title = publication.title,
            publicationType = tempPublicationType,
            price = publication.price
        )

        // Сохраняем новый publicationNew
        val publicationSave = publicationRepository.save(publicationNew)

        // Добавляем в коллекцию (она уже инициализирована)
        tempPublicationType?.publications?.add(publicationSave)

        return publicationSave.convertToResponseDto()
    }

    @Transactional
    fun update(publication: PublicationUpdateRequest) : PublicationResponse {

        // Найти существующее издание
        val existingPublication = publicationRepository.findById(publication.id)
            .orElseThrow { IllegalArgumentException("Publication with ID ${publication.id} not found") }

        val tempPublicationType: PublicationTypeEntity? =
            publicationTypeRepository.findById(publication.publicationTypeId)
                .orElseThrow { IllegalArgumentException("Publication with ID ${publication.id} not found") }

        existingPublication.index = publication.index
        existingPublication.title = publication.title
        existingPublication.price = publication.price
        existingPublication.publicationType = tempPublicationType

        publicationRepository.save(existingPublication)

        tempPublicationType?.publications?.add(existingPublication)

        return existingPublication.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): PublicationResponse {

        // Найти существующее издание
        val existingPublication = publicationRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Publication with ID ${id} not found") }

        // Удалить издание
        publicationRepository.deleteById(id)

        return existingPublication.convertToResponseDto()
    }

}
