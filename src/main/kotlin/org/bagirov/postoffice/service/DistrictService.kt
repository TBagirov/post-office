package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.DistrictRequest
import org.bagirov.postoffice.dto.response.DistrictResponse
import org.bagirov.postoffice.entity.DistrictEntity
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.repository.DistrictRepository
import org.bagirov.postoffice.repository.PostmanRepository
import org.bagirov.postoffice.repository.RegionRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class DistrictService(
    private val districtRepository: DistrictRepository,
    private val regionRepository: RegionRepository,
    private val postmanRepository: PostmanRepository
) {

    fun getById(id: UUID): DistrictResponse = districtRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<DistrictResponse> = districtRepository.findAll().map{it -> it.convertToResponseDto()}

    @Transactional
    fun save(districtRequest: DistrictRequest) : DistrictResponse {
        val tempRegion: RegionEntity? = regionRepository.findById(districtRequest.regionId).orElse(null)
        val tempPostman: PostmanEntity? = postmanRepository.findById(districtRequest.postmanId).orElse(null)

        val district: DistrictEntity = DistrictEntity()

        district.region = tempRegion
        district.postman = tempPostman

        val districtSave: DistrictEntity = districtRepository.save(district)

        tempRegion?.districts?.add(districtSave)
        tempPostman?.districts?.add(districtSave)

        return districtSave.convertToResponseDto()
    }
}