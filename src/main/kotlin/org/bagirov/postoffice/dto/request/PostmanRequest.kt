package org.bagirov.postoffice.dto.request

import jakarta.persistence.*
import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.RefreshTokenEntity
import org.bagirov.postoffice.entity.RoleEntity
import org.bagirov.postoffice.entity.SubscriberEntity
import java.time.LocalDateTime

data class PostmanRequest (
    var name: String,
    var surname: String,
    var patronymic: String,
    private var username: String,
    private var password: String,
    var email: String,
    var phone: String,
    var role: RoleEntity,
)