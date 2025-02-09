package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "publications")
data class PublicationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name="index", nullable = false)
    var index: String,

    @Column(name="title", nullable = false)
    var title: String,

    @ManyToOne()
    @JoinColumn(name = "publication_type_id", nullable = false)
    var publicationType: PublicationTypeEntity?,

    @Column(name="price", nullable = false)
    var price: Int,

    @OneToMany(mappedBy="publication")
    var subscriptions: MutableList<SubscriptionEntity>? = null
)

