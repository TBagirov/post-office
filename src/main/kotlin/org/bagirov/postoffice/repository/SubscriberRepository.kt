package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.SubscriberEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface SubscriberRepository: JpaRepository<SubscriberEntity, UUID> {



}