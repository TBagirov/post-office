package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.RegionEntity
import org.bagirov.postoffice.entity.StreetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface StreetRepository: JpaRepository<StreetEntity, UUID> {


    @Query("""
        SELECT
            *
        FROM
            streets 
        WHERE
            name = :name
    """, nativeQuery = true)
    fun findByName(@Param("name")name: String): StreetEntity?


    @Query("""
        SELECT
            *
        FROM
            streets 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<StreetEntity>

    @Query("""
        SELECT
            *
        FROM
            streets  
        """, nativeQuery = true)
    override fun findAll(): MutableList<StreetEntity>


    @Transactional
    @Query(
        """
        INSERT INTO streets (id, name, region_id)
        VALUES (:id ,:name, :region_id)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun saveStreet(
        @Param("id") id: UUID,
        @Param("name") name: String,
        @Param("region_id") regionId: UUID,
    ): StreetEntity


    @Transactional
    @Query(
        """
    UPDATE streets
    SET name = :name,
        region_id = :region_id
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updateStreet(
        @Param("id") id: UUID,
        @Param("name") name: String,
        @Param("region_id") regionId: UUID?
    ): StreetEntity

    @Modifying
    @Query("""
        DELETE FROM streets WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)
}