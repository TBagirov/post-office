package org.bagirov.postoffice.repository


import org.bagirov.postoffice.dto.response.ReportResponse.projection.ReportSubscriptionByIdSubscriberProjection
import org.bagirov.postoffice.dto.response.ReportResponse.projection.ReportSubscriptionProjection
import org.bagirov.postoffice.entity.PublicationEntity
import org.bagirov.postoffice.entity.SubscriberEntity
import org.bagirov.postoffice.entity.SubscriptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Repository
interface SubscriptionRepository: JpaRepository<SubscriptionEntity, UUID> {

    fun findBySubscriberAndPublication(subscriber: SubscriberEntity, publication: PublicationEntity): SubscriptionEntity?


    @Query("""
        SELECT
            *
        FROM
            subscriptions 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<SubscriptionEntity>

    @Query("""
        SELECT
            *
        FROM
            subscriptions  
        """, nativeQuery = true)
    override fun findAll(): MutableList<SubscriptionEntity>


    @Transactional
    @Query(
        """
        INSERT INTO subscriptions (id, subscriber_id, publication_id, start_date, duration)
        VALUES (:id ,:subscriber_id, :publication_id, :start_date, :duration)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun saveSubscription(
        @Param("id") id: UUID,
        @Param("subscriber_id") subscriberId: UUID,
        @Param("publication_id") publicationId: UUID,
        @Param("start_date") startDate: LocalDateTime,
        @Param("duration") duration: Int
    ): SubscriptionEntity


    @Transactional
    @Query(
        """
    UPDATE subscriptions
    SET subscriber_id = :subscriber_id, 
        publication_id = :publication_id,
        start_date = :start_date,
        duration = :duration
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updateSubscription(
        @Param("id") id: UUID,
        @Param("subscriber_id") subscriberId: UUID,
        @Param("publication_id") publicationId: UUID,
        @Param("start_date") startDate: LocalDateTime,
        @Param("duration") duration: Int
    ): SubscriptionEntity

    @Modifying
    @Query("""
        DELETE FROM subscriptions WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)



    @Query(
        """
        SELECT 
            sub.id as subscriptionId,
            s.id as subscriberId,
            p.id AS publicationId,
            CONCAT(s.surname, ' ', s.name, ' ', s.patronymic) AS fioSubscriber,
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