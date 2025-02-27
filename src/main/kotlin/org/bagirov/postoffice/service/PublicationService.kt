package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.PublicationRequest
import org.bagirov.postoffice.dto.request.update.PublicationUpdateRequest
import org.bagirov.postoffice.dto.response.PublicationResponse
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
    private val publicationTypeRepository: PublicationTypeRepository,
) {

    fun getById(id: UUID): PublicationResponse =
        publicationRepository.findById(id)
            .orElseThrow { NoSuchElementException("Publication with ID ${id} not found") }
            .convertToResponseDto()

    fun getAll(): List<PublicationResponse> =
        publicationRepository.findAll().map { it.convertToResponseDto() }

    @Transactional
    fun save(publication: PublicationRequest): PublicationResponse {

        // Если тип издания существует, используем его, иначе создаем новый.
        val publicationType = publicationTypeRepository.findByType(publication.publicationType)
            ?: publicationTypeRepository.save(PublicationTypeEntity(type = publication.publicationType))

        val publicationNew = PublicationEntity(
            index = publication.index,
            title = publication.title,
            publicationType = publicationType,
            price = publication.price
        )

        // Сохраняем новый publicationNew
        val publicationSave = publicationRepository.save(publicationNew)

        // Добавляем в коллекцию (она уже инициализирована)
        publicationType.publications?.add(publicationSave)

        return publicationSave.convertToResponseDto()
    }

    @Transactional
    fun update(publication: PublicationUpdateRequest): PublicationResponse {

        // Найти существующее издание
        val existingPublication = publicationRepository.findById(publication.id)
            .orElseThrow { NoSuchElementException("Publication with ID ${publication.id} not found") }

        val tempPublicationType: PublicationTypeEntity? =
            publicationTypeRepository.findById(publication.publicationTypeId)
                .orElseThrow { NoSuchElementException("Publication with ID ${publication.id} not found") }

        existingPublication.index = publication.index
        existingPublication.title = publication.title
        existingPublication.price = publication.price
        existingPublication.publicationType = tempPublicationType

        val savePublication = publicationRepository.save(existingPublication)

        if (tempPublicationType?.publications?.contains(savePublication) == true)
            tempPublicationType.publications?.add(savePublication)

        return savePublication.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): PublicationResponse {

        // Найти существующее издание
        val existingPublication = publicationRepository.findById(id)
            .orElseThrow { NoSuchElementException("Publication with ID ${id} not found") }

        // Удалить издание
        publicationRepository.delete(existingPublication)

        return existingPublication.convertToResponseDto()
    }

}
