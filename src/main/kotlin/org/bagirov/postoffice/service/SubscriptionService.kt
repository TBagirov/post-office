package org.bagirov.postoffice.service


import org.bagirov.postoffice.dto.request.SubscriptionRequest
import org.bagirov.postoffice.dto.request.update.SubscriptionUpdateRequest
import org.bagirov.postoffice.dto.response.SubscriptionResponse
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.SubscriberEntity
import org.bagirov.postoffice.entity.SubscriptionEntity
import org.bagirov.postoffice.repository.PublicationRepository
import org.bagirov.postoffice.repository.SubscriberRepository
import org.bagirov.postoffice.repository.SubscriptionRepository
import org.bagirov.postoffice.repository.UserRepository
import org.bagirov.postoffice.utill.convertToResponseDto
import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.spi.InFlightMetadataCollector.DuplicateSecondaryTableException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class SubscriptionService(
    private val subscriptionRepository: SubscriptionRepository,
    private val subscriberRepository: SubscriberRepository,
    private val publicationRepository: PublicationRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    fun getById(id: UUID): SubscriptionResponse = subscriptionRepository.findById(id)
        .orElseThrow{ NoSuchElementException("Subscription with ID ${id} not found") }
        .convertToResponseDto()

    fun getAll():List<SubscriptionResponse> = subscriptionRepository.findAll().map{ it.convertToResponseDto()}

    @Transactional
    fun save(token: String, subscriptionRequest: SubscriptionRequest): SubscriptionResponse {

        val user = userRepository.findById(UUID.fromString(jwtService.getId(token)))
            .orElseThrow{ NoSuchElementException("Запрос от несуществующего пользователя") }


        val tempSubscriber: SubscriberEntity = subscriberRepository.findById(user.subscriber?.id!!).orElse(null)
        val tempPublication: PublicationEntity = publicationRepository.findById(subscriptionRequest.publicationId).orElse(null)

        if(subscriptionRepository.findBySubscriberAndPublication(tempSubscriber, tempPublication) != null)
            throw DuplicateSecondaryTableException(Identifier("subscriptions", true))

        val subscriptionNew = SubscriptionEntity(
            subscriber = tempSubscriber,
            publication = tempPublication,
            duration = subscriptionRequest.duration,
            startDate = LocalDateTime.now()
        )

        val subscriptionSave: SubscriptionEntity = subscriptionRepository.save(subscriptionNew)

        //subscriptions
        tempSubscriber.subscriptions?.add(subscriptionSave)
        tempPublication.subscriptions?.add(subscriptionSave)

        return subscriptionSave.convertToResponseDto()
    }

    @Transactional
    fun update(token: String, subscription: SubscriptionUpdateRequest) : SubscriptionResponse {
        val user = userRepository.findById(UUID.fromString(jwtService.getId(token)))
            .orElseThrow{ NoSuchElementException("Запрос от несуществующего пользователя") }

        // Найти существующего подписчика
        val existingSubscription = subscriptionRepository.findById(subscription.id)
            .orElseThrow { NoSuchElementException("Subscription with ID ${subscription.id} not found") }

        val tempSubscriber: SubscriberEntity = subscriberRepository
            .findById(user.subscriber?.id!!).orElse(null)

        val tempPublication: PublicationEntity = publicationRepository
            .findById(subscription.publicationId).orElse(null)

        existingSubscription.publication = tempPublication
        existingSubscription.subscriber = tempSubscriber
        existingSubscription.startDate = subscription.startDate
        existingSubscription.duration = subscription.duration

        val subscriptionUpdate: SubscriptionEntity = subscriptionRepository.save(existingSubscription)

        tempSubscriber.subscriptions?.add(subscriptionUpdate)
        tempPublication.subscriptions?.add(subscriptionUpdate)

        return subscriptionUpdate.convertToResponseDto()
    }

    @Transactional
    fun delete(id: UUID): SubscriptionResponse {

        // Найти существующую подписку
        val existingSubscription = subscriptionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Subscription with ID ${id} not found") }

        // Удалить издание
        subscriptionRepository.deleteById(id)

        return existingSubscription.convertToResponseDto()
    }


}
