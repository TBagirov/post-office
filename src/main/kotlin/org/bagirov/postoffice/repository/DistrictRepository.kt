package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.DistrictEntity
import org.bagirov.postoffice.entity.RegionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
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
}