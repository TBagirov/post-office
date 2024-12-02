package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.response.RegionResponse
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
    fun save(region: RegionEntity):RegionResponse = regionRepository.save(region).convertToResponseDto()
}
