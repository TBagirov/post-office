package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.SubscriberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface SubscriberRepository: JpaRepository<SubscriberEntity, UUID> {


    @Query("""
        SELECT
            *
        FROM
            subscribers 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<SubscriberEntity>

    @Query("""
        SELECT
            *
        FROM
            subscribers  
        """, nativeQuery = true)
    override fun findAll(): MutableList<SubscriberEntity>


    @Transactional
    @Query(
        """
        INSERT INTO subscribers (id, name, surname, patronymic, building, sub_address, street_id, district_id)
        VALUES (:id ,:name, :surname, :patronymic, :building, :sub_address, :street_id, :district_id)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun saveSubscriber(
        @Param("id") id: UUID,
        @Param("name") name: String,
        @Param("surname") surname: String,
        @Param("patronymic") patronymic: String,
        @Param("building") building: String,
        @Param("sub_address") subAddress: String?,
        @Param("street_id") streetId: UUID,
        @Param("district_id") districtId: UUID
    ): SubscriberEntity


    @Transactional
    @Query(
        """
    UPDATE subscribers
    SET name = :name, 
        surname = :surname,
        patronymic = :patronymic,
        building = :building,
        sub_address = :sub_address,
        street_id = :street_id,
        district_id = :district_id
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updateSubscriber(
        @Param("id") id: UUID,
        @Param("name") name: String,
        @Param("surname") surname: String,
        @Param("patronymic") patronymic: String,
        @Param("building") building: String,
        @Param("sub_address") subAddress: String?,
        @Param("street_id") streetId: UUID,
        @Param("district_id") districtId: UUID
    ): SubscriberEntity

    @Modifying
    @Query("""
        DELETE FROM subscribers WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)


}