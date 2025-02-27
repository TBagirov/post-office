package org.bagirov.postoffice.repository


import org.bagirov.postoffice.dto.response.ReportResponse.projection.ReportSubscriptionByIdSubscriberProjection
import org.bagirov.postoffice.dto.response.ReportResponse.projection.ReportSubscriptionProjection
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.SubscriberEntity
import org.bagirov.postoffice.entity.SubscriptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SubscriptionRepository: JpaRepository<SubscriptionEntity, UUID> {

    fun findBySubscriberAndPublication(subscriber: SubscriberEntity, publication: PublicationEntity): SubscriptionEntity?





    @Query(
        """
        SELECT 
            sub.id as subscriptionId,
            s.id as subscriberId,
            p.id AS publicationId,
            CONCAT(u.surname, ' ', u.name, ' ', u.patronymic) AS fioSubscriber,
            p.title AS titlePublication,
            sub.start_date AS startDateSubscription,
            sub.start_date + INTERVAL '1 month' * sub.duration AS endDateSubscription,
            CASE 
                WHEN NOW() < (sub.start_date + INTERVAL '1 month' * sub.duration) THEN 'Активна'
                ELSE 'Законченна'
            END AS statusSubscription
        FROM 
            subscriptions sub
        JOIN 
            subscribers s ON sub.subscriber_id = s.id
        JOIN 
            users u on s.user_id = u.id
        LEFT JOIN 
            publications p ON sub.publication_id = p.id
        """,
        nativeQuery = true
    )
    fun generateReport(): List<ReportSubscriptionProjection>


    @Query(
        """
        SELECT 
            sub.id as subscriptionId,
            p.id AS publicationId,
            p.title AS title,
            pt.name AS type,
            sub.start_date AS startDate,
            sub.start_date + INTERVAL '1 month' * sub.duration AS endDate,
            p.price AS price
        FROM 
            subscriptions sub
        JOIN 
            subscribers s ON sub.subscriber_id = s.id
        LEFT JOIN 
            publications p ON sub.publication_id = p.id
        LEFT JOIN 
            publication_types pt ON p.publication_type_id = pt.id
        WHERE 
            sub.subscriber_id = :subscriberId
        """,
        nativeQuery = true
    )
    fun generateReportBySubscriberId(@Param("subscriberId") subscriberId: UUID): List<ReportSubscriptionByIdSubscriberProjection>




}