package org.bagirov.postoffice.repository

import org.bagirov.postoffice.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoleRepository : JpaRepository<RoleEntity, UUID> {

    fun findByName(name: String): RoleEntity?
}