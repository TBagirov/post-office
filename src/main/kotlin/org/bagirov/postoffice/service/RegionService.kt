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
    private val regionRepository: RegionRepository,
    private val districtService: DistrictService
) {

    fun getById(id: UUID): RegionResponse = regionRepository.findById(id)
        .orElseThrow{ NoSuchElementException("Region with ID ${id} not found") }
        .convertToResponseDto()

    fun getAll():List<RegionResponse> = regionRepository.findAll().map{ it.convertToResponseDto()}

    @Transactional
    fun saveEnt(region: RegionEntity):RegionEntity {

        val regionSave = regionRepository.save(region)

        districtService.saveOnlyRegion(regionSave)

       return region
    }

    @Transactional
    fun save(region: RegionEntity): RegionResponse {
        return saveEnt(region).convertToResponseDto()
    }

    @Transactional
    fun update(region: RegionEntity): RegionResponse {

        // Найти существующий регион
        val existingRegion = regionRepository.findById(region.id!!)
            .orElseThrow { NoSuchElementException("Region with ID ${region.id} not found") }

        existingRegion.name = region.name

        // Выполнить обновление в базе данных
        regionRepository.save(existingRegion)

        return existingRegion.convertToResponseDto()
    }

    // TODO: удаляет, но в ответ 500 код, разобраться почему
    @Transactional
    fun delete(id: UUID): RegionResponse {
        // Найти существующий регион
        val existingRegion = regionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Region with ID ${id} not found") }

        // Обнуляем ссылку для каждой улицы, чтобы удалить связь с регионом
        existingRegion.streets?.forEach { street ->
            street.region = null
        }

        existingRegion.streets?.clear()

        // Удалить регион
        regionRepository.deleteById(id)

        return existingRegion.convertToResponseDto()
    }

}
