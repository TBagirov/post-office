package org.bagirov.postoffice.repository

import org.bagirov.postoffice.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {

    fun findByUsername(username: String): Optional<UserEntity>
}