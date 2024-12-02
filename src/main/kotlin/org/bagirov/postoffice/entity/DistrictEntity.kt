package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="districts")
data class DistrictEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "postman_id", referencedColumnName = "id")
    var postman: PostmanEntity? = null,

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    var region: RegionEntity? = null,

    @OneToMany(mappedBy = "district")
    var subscribers:MutableList<SubscriberEntity>? = null

)