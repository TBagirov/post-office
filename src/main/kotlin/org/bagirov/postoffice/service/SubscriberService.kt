package org.bagirov.postoffice.service

import io.jsonwebtoken.ExpiredJwtException
import org.bagirov.postoffice.dto.request.StreetRequest
import org.bagirov.postoffice.dto.request.SubscriberRequest
import org.bagirov.postoffice.dto.request.update.SubscriberUpdateRequest
import org.bagirov.postoffice.dto.response.StreetResponse
import org.bagirov.postoffice.dto.response.SubscriberResponse
import org.bagirov.postoffice.entity.DistrictEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.bagirov.postoffice.entity.SubscriberEntity
import org.bagirov.postoffice.entity.UserEntity
import org.bagirov.postoffice.props.Role
import org.bagirov.postoffice.repository.*
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
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
) {

    fun getById(id: UUID): SubscriberResponse =
        subscriberRepository
            .findById(id)
            .orElseThrow { NoSuchElementException("Subscriber with ID ${id} not found") }
            .convertToResponseDto()

    fun getAll(): List<SubscriberResponse> =
        subscriberRepository.findAll().map { it.convertToResponseDto() }

    @Transactional
    fun save(currentUser: UserEntity, subscriberRequest: SubscriberRequest): SubscriberResponse {

        val user = userRepository.findById(currentUser.id!!)
            .orElseThrow { NoSuchElementException("Запрос от несуществующего пользователя") }

        // Получаем или создаем улицу по имени
        val street: StreetResponse = streetRepository.findByName(subscriberRequest.street)
            ?.convertToResponseDto()
            ?: streetService.save(StreetRequest(subscriberRequest.street))

        val districtList = districtRepository.findByRegionName(street.regionName!!).orElse(null)
        if (districtList.isEmpty()) {
            throw NoSuchElementException("Нет районов для региона ${street.regionName}")
        }
        val districtRes = districtList.random()

        val streetEntity = StreetEntity(
            id = street.id,
            name = street.name,
            region = districtRes.region
        )

        val subscriberNew = SubscriberEntity(
            user = user,
            district = districtRes,
            building = subscriberRequest.building,
            subAddress = subscriberRequest.subAddress,
            street = streetEntity
        )
        val subscriberSave = subscriberRepository.save(subscriberNew)

        val roleSubscriber = roleRepository.findByName(Role.SUBSCRIBER)
            ?: throw NoSuchElementException("Роли ${Role.SUBSCRIBER} нет в базе данных!")
        user.role = roleSubscriber

        val userSave = userRepository.save(user)
        roleSubscriber.users?.add(userSave)

        districtRes.subscribers?.add(subscriberSave)
        streetEntity.subscribers?.add(subscriberSave)
        userSave.subscriber = subscriberSave

        return subscriberSave.convertToResponseDto()
    }

    @Transactional
    fun update(currentUser: UserEntity, subscriberRequest: SubscriberUpdateRequest): SubscriberResponse {

        val user = userRepository.findById(currentUser.id!!)
            .orElseThrow { NoSuchElementException("Запрос от несуществующего пользователя") }

        val subscriber = user.subscriber
            ?: throw NoSuchElementException("Subscriber profile not found for user with ID ${currentUser.id}")

        val tempStreet: StreetEntity = streetRepository
            .findById(subscriberRequest.streetId).orElse(null)

        val tempDistrict: DistrictEntity = districtRepository
            .findById(subscriberRequest.districtId).orElse(null)

        user.subscriber!!.street = tempStreet
        user.subscriber!!.district = tempDistrict
        user.subscriber!!.subAddress = subscriberRequest.subAddress
        user.subscriber!!.building = subscriberRequest.building

        val subscriberSave: SubscriberEntity = subscriberRepository.save(subscriber)

        tempStreet.subscribers?.add(subscriberSave)
        tempDistrict.subscribers?.add(subscriberSave)

        return subscriberSave.convertToResponseDto()
    }


    @Transactional
    fun delete(currentUser: UserEntity): SubscriberResponse {
        val user = userRepository.findById(currentUser.id!!)
            .orElseThrow { IllegalArgumentException("Subscriber with ID ${currentUser.id} not found") }

        // Найти существующего подписчика
        val existingSubscriber = user.subscriber
            ?: throw NoSuchElementException("Subscriber with ID ${currentUser.id} not found")

        // Удалить подписчика
        userRepository.delete(user)

        return existingSubscriber.convertToResponseDto()
    }


}
