package org.bagirov.postoffice.repository


import org.bagirov.postoffice.entity.PostmanEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PostmanRepository: JpaRepository<PostmanEntity, UUID> {


}