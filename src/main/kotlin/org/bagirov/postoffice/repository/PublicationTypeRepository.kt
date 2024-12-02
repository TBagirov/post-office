package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.PublicationTypeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PublicationTypeRepository: JpaRepository<PublicationTypeEntity, UUID> {

    fun findByType(type: String): PublicationTypeEntity?
}