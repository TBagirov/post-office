package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "publication_types")
data class PublicationTypeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name="name", nullable = false)
    var type: String,

    @OneToMany(mappedBy = "publicationType")
    var publications: MutableList<PublicationEntity>? = null


)
