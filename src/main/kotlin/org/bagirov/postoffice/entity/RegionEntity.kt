package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "regions")
data class RegionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name="name", nullable = false, unique = true)
    var name: String,

    @OneToMany(mappedBy = "region")
    var streets: MutableSet<StreetEntity>? = null,

    @OneToMany(mappedBy = "region")
    var districts: MutableList<DistrictEntity>? = null

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RegionEntity

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        return result
    }

    // override fun toString(): String {return name}
}
