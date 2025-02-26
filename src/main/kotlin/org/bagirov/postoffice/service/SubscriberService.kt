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
    private val jwtService: JwtService,
) {

    fun getById(id: UUID): SubscriberResponse = subscriberRepository.findById(id)
        .orElseThrow{ NoSuchElementException("Subscriber with ID ${id} not found") }
        .convertToResponseDto()

    fun getAll():List<SubscriberResponse> = subscriberRepository.findAll().map {it.convertToResponseDto() }

    @Transactional
    fun save(token: String, subscriberRequest: SubscriberRequest): SubscriberResponse {
        // TODO: когда будет больше ф-ций, проверить можно ли использовать такой способ извлечения токена
//        val authentication = SecurityContextHolder.getContext().authentication
//        val jwt = authentication.credentials as? String ?:
//        throw BadCredentialsException("Token cannot be null")


        if(!jwtService.isValidExpired(token)){
            val claims = jwtService.extractClaims(token) // Получение claims из токена
            throw ExpiredJwtException(null, claims, "Token Expired")
        }

        val user = userRepository.findById(UUID.fromString(jwtService.getId(token)))
            .orElseThrow{ NoSuchElementException("Запрос от несуществующего пользователя") }

        val roleSubscriber = roleRepository.findByName("SUBSCRIBER") ?:
            throw NoSuchElementException("Роли SUBSCRIBER нет в базе данных!")

        user.role = roleSubscriber
        val userSave = userRepository.save(user)
        roleSubscriber.users?.add(userSave)

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

        val subscriberNew = SubscriberEntity (
            user = userSave,
            district = districtRes,
            building = subscriberRequest.building,
            subAddress = subscriberRequest.subAddress,
            street = streetEntity
        )

        val subscriberSave:SubscriberEntity = subscriberRepository.save(subscriberNew)

        districtRes.subscribers?.add(subscriberSave)
        streetEntity.subscribers?.add(subscriberSave)
        userSave.subscriber = subscriberSave

        return subscriberSave.convertToResponseDto()
    }

    @Transactional
    fun update(token: String, subscriber: SubscriberUpdateRequest) : SubscriberResponse {


        if(!jwtService.isValidExpired(token)){
            val claims = jwtService.extractClaims(token) // Получение claims из токена
            throw ExpiredJwtException(null, claims, "Token Expired")
        }

        val user = userRepository.findById(UUID.fromString(jwtService.getId(token)))
            .orElseThrow{ NoSuchElementException("Запрос от несуществующего пользователя") }


        val tempStreet: StreetEntity = streetRepository
            .findById(subscriber.streetId).orElse(null)

        val tempDistrict: DistrictEntity = districtRepository
            .findById(subscriber.districtId).orElse(null)


        user.subscriber!!.street = tempStreet
        user.subscriber!!.district = tempDistrict
        user.subscriber!!.subAddress = subscriber.subAddress
        user.subscriber!!.building = subscriber.building

        val subscriberUpdate: SubscriberEntity = subscriberRepository.save(user.subscriber!!)

        tempStreet.subscribers?.add(subscriberUpdate)
        tempDistrict.subscribers?.add(subscriberUpdate)

        return subscriberUpdate.convertToResponseDto()
    }


    @Transactional
    fun delete(token: String): SubscriberResponse {
        if(!jwtService.isValidExpired(token))
            throw IllegalArgumentException("Token Expired")

        val userId =UUID.fromString(jwtService.getId(token))

        // Найти существующего подписчика
        val existingSubscriber = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("Subscriber with ID ${userId} not found") }

        val sub = existingSubscriber.subscriber

        // Удалить подписчика
        userRepository.deleteById(userId)

        return sub!!.convertToResponseDto()
    }


}
