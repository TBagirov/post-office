package org.bagirov.postoffice.service
import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.SubscriberRequest
import org.bagirov.postoffice.dto.request.update.SubscriberUpdateRequest
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
        val districtRes: DistrictEntity = districtRepository.findByRegionName(street.regionName!!).orElse(null).random()

        val streetEntity: StreetEntity = StreetEntity(
            id = street.id,
            name = street.name,
            region = districtRes.region
        )

        val subscriberSave:SubscriberEntity = subscriberRepository.saveSubscriber(
            id = UUID.randomUUID(),
            name = subscriberRequest.name,
            surname = subscriberRequest.surname,
            patronymic = subscriberRequest.patronymic,
            districtId = districtRes.id!!,
            building = subscriberRequest.building,
            subAddress = subscriberRequest.subAddress,
            streetId = streetEntity.id!!
        )

        districtRes.subscribers?.add(subscriberSave)

        return subscriberSave.convertToResponseDto()
    }

    @Transactional
    fun update(subscriber: SubscriberUpdateRequest) : SubscriberResponse {

        // Найти существующего подписчика
        val existingSubscriber = subscriberRepository.findById(subscriber.id)
            .orElseThrow { IllegalArgumentException("Subscriber with ID ${subscriber.id} not found") }


        val tempStreet: StreetEntity = streetRepository
            .findById(subscriber.streetId).orElse(null)

        val tempDistrict: DistrictEntity = districtRepository
            .findById(subscriber.districtId).orElse(null)

        val subscriberUpdate: SubscriberEntity = subscriberRepository.updateSubscriber(
            id = subscriber.id,
            name = subscriber.name,
            surname = subscriber.surname,
            patronymic = subscriber.patronymic,
            building = subscriber.building,
            subAddress = subscriber.subAddress,
            streetId = subscriber.streetId,
            districtId = subscriber.districtId
        )

        existingSubscriber.street = tempStreet
        existingSubscriber.district = tempDistrict


        existingSubscriber.name = subscriber.name
        existingSubscriber.surname = subscriber.surname
        existingSubscriber.patronymic = subscriber.patronymic
        existingSubscriber.subAddress = subscriber.subAddress
        existingSubscriber.building = subscriber.building

        return subscriberUpdate.convertToResponseDto()
    }


    @Transactional
    fun delete(id: UUID): SubscriberResponse {

        // Найти существующее издание
        val existingPublication = subscriberRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Subscriber with ID ${id} not found") }

        // Удалить издание
        subscriberRepository.deleteById(id)

        return existingPublication.convertToResponseDto()
    }


}
