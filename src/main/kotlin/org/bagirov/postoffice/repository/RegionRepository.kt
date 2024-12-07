package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.DistrictEntity
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface RegionRepository: JpaRepository<RegionEntity, UUID> {


    @Query("""
        SELECT
            *
        FROM
            regions 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<RegionEntity>

    @Query("""
        SELECT
            *
        FROM
            regions  
        """, nativeQuery = true)
    override fun findAll(): MutableList<RegionEntity>


    @Transactional
    @Query(
        """
        INSERT INTO regions (id, name)
        VALUES (:id ,:name)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun saveRegion(
        @Param("id") id: UUID,
        @Param("name") name: String,
    ): RegionEntity


    @Transactional
    @Query(
        """
    UPDATE regions
    SET name = :name
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updateRegion(
        @Param("id") id: UUID,
        @Param("name") name: String,
    ): RegionEntity


    @Modifying
    @Query("""
        DELETE FROM regions WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)
}