package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.PublicationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PublicationRepository: JpaRepository<PublicationEntity, UUID> {
}