package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.PublicationRequest
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
    private val publicationTypeRepository: PublicationTypeRepository
) {

    fun getById(id: UUID): PublicationResponse =  publicationRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<PublicationResponse> =  publicationRepository.findAll().map { it -> it.convertToResponseDto() }

    @Transactional
    fun save(publication: PublicationRequest) : PublicationResponse {

        var tempPublicationType: PublicationTypeEntity? = publicationTypeRepository
            .findByType(publication.publicationType)

        if(tempPublicationType == null){
            val pubType: PublicationTypeEntity = PublicationTypeEntity(type = publication.publicationType)
            tempPublicationType = publicationTypeRepository.save(pubType)
        }

        val publication: PublicationEntity = PublicationEntity(
            id = null,
            index = publication.index,
            title = publication.title,
            publicationType = tempPublicationType,
            price = publication.price
        )

        val publicationSave: PublicationEntity = publicationRepository.save(publication)

        tempPublicationType.publications?.add(publicationSave)

        return publicationSave.convertToResponseDto()
    }
}
