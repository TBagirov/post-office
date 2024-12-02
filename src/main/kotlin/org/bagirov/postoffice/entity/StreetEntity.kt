package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="streets")
data class StreetEntity(

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    var id: UUID? = null,

    @Column(name = "name", nullable=false, unique = true)
    var name: String,

    @ManyToOne
    @JoinColumn(name="region_id", referencedColumnName = "id")
    var region: RegionEntity? = null,

    @OneToMany(mappedBy = "street")
    var subscrbers: MutableSet<SubscriberEntity>? = null,

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StreetEntity

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }

    //override fun toString(): String {return name}
}
