package org.bagirov.postoffice.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name="subscribers")
data class SubscriberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name="name", nullable = false)
    var name: String,

    @Column(name="surname", nullable = false)
    var surname: String,

    @Column(name="patronymic", nullable = false)
    var patronymic: String,

    @ManyToOne
    @JoinColumn(name="district_id", referencedColumnName = "id")
    var district: DistrictEntity?,

    @ManyToOne
    @JoinColumn(name="street_id", referencedColumnName = "id")
    var street: StreetEntity?,

    @Column(name="building", nullable = false)
    var building: String,

    @Column(name="sub_address", nullable = true)
    var subAddress: String? = null,

    @OneToMany(mappedBy = "subscriber")
    var subscriptions: MutableSet<SubscriptionEntity>? = null
){
    fun getFio() = listOfNotNull(surname, name, patronymic)
        .joinToString(" ")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubscriberEntity

        if (id != other.id) return false
        if (name != other.name) return false
        if (surname != other.surname) return false
        if (patronymic != other.patronymic) return false
        if (district != other.district) return false
        if (street != other.street) return false
        if (building != other.building) return false
        if (subAddress != other.subAddress) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + patronymic.hashCode()
        result = 31 * result + district.hashCode()
        result = 31 * result + street.hashCode()
        result = 31 * result + building.hashCode()
        result = 31 * result + (subAddress?.hashCode() ?: 0)
        return result
    }
}
