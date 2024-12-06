package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.DistrictEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface DistrictRepository: JpaRepository<DistrictEntity, UUID> {


    @Query("""
        SELECT
            d.id as id,
            postman_id,
            region_id, 
            r.name
        FROM
            districts as d join regions as r on d.region_id = r.id
        WHERE
            r.name like :regionName
        """, nativeQuery = true)
    fun findByRegionName(@Param("regionName") regionName: String): Optional<List<DistrictEntity>>

    @Query("""
        SELECT
            *
        FROM
            districts 
        WHERE
            id = :id
        """, nativeQuery = true)
    override fun findById(@Param("id") id: UUID): Optional<DistrictEntity>

    @Query("""
        SELECT
            *
        FROM
            districts  
        """, nativeQuery = true)
    override fun findAll(): MutableList<DistrictEntity>


    @Transactional
    @Query(
        """
        INSERT INTO districts (id, postman_id, region_id)
        VALUES (:id ,:postman_id, :region_id)
        RETURNING *
        """,
        nativeQuery = true
    )
    fun saveDistrict(
        @Param("id") id: UUID,
        @Param("postman_id") postmanId: UUID?,
        @Param("region_id") regionId: UUID?
    ): DistrictEntity


    @Transactional
    @Query(
        """
    UPDATE districts
    SET postman_id = :postmanId,
        region_id = :regionId
    WHERE id = :id
    RETURNING *
    """,
        nativeQuery = true
    )
    fun updateDistrict(
        @Param("id") id: UUID,
        @Param("postmanId") postmanId: UUID?,
        @Param("regionId") regionId: UUID?
    ): DistrictEntity


    @Modifying
    @Query("""
        DELETE FROM districts WHERE id = :id
    """, nativeQuery = true)
    override fun deleteById(@Param("id") id: UUID)

}