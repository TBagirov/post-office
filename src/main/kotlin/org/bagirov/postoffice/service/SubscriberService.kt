package org.bagirov.postoffice.service
import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.SubscriberRequest
import org.bagirov.postoffice.dto.response.StreetResponse
import org.bagirov.postoffice.dto.response.SubscriberResponse
import org.bagirov.postoffice.entity.*
import org.bagirov.postoffice.repository.DistrictRepository
import org.bagirov.postoffice.repository.StreetRepository
import org.bagirov.postoffice.repository.SubscriberRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SubscriberService(
    private val subscriberRepository: SubscriberRepository,
    private val districtRepository: DistrictRepository,
    private val streetRepository: StreetRepository,
    private val streetService: StreetService,
) {

    fun getById(id: UUID): SubscriberResponse = subscriberRepository.findById(id).orElse(null).convertToResponseDto()

    fun getAll():List<SubscriberResponse> = subscriberRepository.findAll().map { it -> it.convertToResponseDto() }

    @Transactional
    fun save(subscriberRequest: SubscriberRequest): SubscriberResponse {


        val tempStreet: StreetEntity? = streetRepository.findByName(subscriberRequest.street)

        var street: StreetResponse? = null
        if(tempStreet == null){
            street = streetService.save(StreetRequest(subscriberRequest.street))
        } else{
            street = tempStreet.convertToResponseDto()
        }

        // TODO: если улицы не существует и она добавилась и попала в район которому не назначен почтальон,
        //  т.е. этого региона нет в district, то летит ошибка 500
        val districtRes: DistrictEntity = districtRepository.findByRegionName(street.regionName).orElse(null).random()

        val streetEntity: StreetEntity = StreetEntity(
            id = street.id,
            name = street.name,
            region = districtRes.region
        )

        val subscriber: SubscriberEntity = SubscriberEntity(
            id = null,
            name = subscriberRequest.name,
            surname = subscriberRequest.surname,
            patronymic = subscriberRequest.patronymic,
            district = districtRes,
            building = subscriberRequest.building,
            subAddress = subscriberRequest.subAddress,
            street = streetEntity
        )

        val subscriberSave:SubscriberEntity = subscriberRepository.save(subscriber)

        districtRes.subscribers?.add(subscriberSave)

        return subscriberSave.convertToResponseDto()
    }
}
