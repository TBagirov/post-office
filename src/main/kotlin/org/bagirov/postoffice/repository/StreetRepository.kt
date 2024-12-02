package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.StreetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StreetRepository: JpaRepository<StreetEntity, UUID> {

    fun findByName(name: String): StreetEntity?
}