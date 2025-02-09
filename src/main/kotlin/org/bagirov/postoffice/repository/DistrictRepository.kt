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

    fun findByRegionName(regionName: String): Optional<List<DistrictEntity>>


}