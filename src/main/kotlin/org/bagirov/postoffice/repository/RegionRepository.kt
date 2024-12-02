package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.RegionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RegionRepository: JpaRepository<RegionEntity, UUID> {

}