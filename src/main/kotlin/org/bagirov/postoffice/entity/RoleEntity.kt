package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "roles")
data class RoleEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(mappedBy = "role")
    var users: MutableSet<UserEntity>? = null
)
