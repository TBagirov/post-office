package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "refresh_tokens")
data class RefreshTokenEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name = "token")
    var token: String? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity

)