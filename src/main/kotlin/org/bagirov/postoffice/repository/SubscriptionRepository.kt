package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.SubscriberEntity
import org.bagirov.postoffice.entity.SubscriptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SubscriptionRepository: JpaRepository<SubscriptionEntity, UUID> {

    fun findBySubscriberAndPublication(subscriber: SubscriberEntity, publication: PublicationEntity): SubscriptionEntity?
}