package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.DistrictRequest
import org.bagirov.postoffice.dto.request.update.DistrictUpdateRequest
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

    fun getAll():List<DistrictResponse> = districtRepository.findAll().map{ it.convertToResponseDto() }

    @Transactional
    fun save(districtRequest: DistrictRequest) : DistrictResponse {
        val tempRegion: RegionEntity? = regionRepository.findById(districtRequest.regionId)
            .orElseThrow{IllegalArgumentException("")}

        val tempPostman: PostmanEntity? = postmanRepository.findById(districtRequest.postmanId)
            .orElseThrow{IllegalArgumentException("")}

        val district: DistrictEntity = DistrictEntity(
            region = tempRegion,
            postman = tempPostman
        )

        districtRepository.save(district)

        tempRegion?.districts?.add(district)
        tempPostman?.districts?.add(district)

        return district.convertToResponseDto()
    }

    @Transactional
    fun update(district: DistrictUpdateRequest): DistrictResponse {

        // Найти существующее отношение почтальонов к районам
        val existingDistrict = districtRepository.findById(district.id!!)
            .orElseThrow { IllegalArgumentException("District with ID ${district.id} not found") }

        val tempRegion: RegionEntity? = regionRepository.findById(district.regionId).orElse(null)
        val tempPostman: PostmanEntity? = postmanRepository.findById(district.postmanId).orElse(null)

        existingDistrict.region = tempRegion
        existingDistrict.postman = tempPostman

        // Выполнить обновление в базе данных
        districtRepository.save(existingDistrict)

        // Обновить связи
        tempRegion?.districts?.add(existingDistrict)
        tempPostman?.districts?.add(existingDistrict)

        return existingDistrict.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): DistrictResponse {
        // Найти существующее отношение почтальонов к районам
        val existingDistrict = districtRepository.findById(id)
            .orElseThrow { IllegalArgumentException("District with ID $id not found") }

        // Удалить отношение почтальонов к районам
        districtRepository.deleteById(id)

        // Преобразовать удалённое отношение почтальонов к районам в DTO и вернуть
        return existingDistrict.convertToResponseDto()
    }

}