package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "postmans")
data class PostmanEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    var user: UserEntity,

    @OneToMany(mappedBy = "postman")
    var districts: MutableList<DistrictEntity>? = null

)
