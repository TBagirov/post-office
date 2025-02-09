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

}