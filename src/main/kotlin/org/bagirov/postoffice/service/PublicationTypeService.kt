package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.PublicationTypeRequest
import org.bagirov.postoffice.dto.response.PublicationTypeResponse
import org.bagirov.postoffice.repository.PublicationTypeRepository
import org.bagirov.postoffice.utill.convertToEntity
import org.bagirov.postoffice.utill.convertToResponseDto
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
    fun save(publicationType: PublicationTypeRequest): PublicationTypeResponse{
        return publicationTypeRepository.save(publicationType.convertToEntity()).convertToResponseDto()
    }
}
