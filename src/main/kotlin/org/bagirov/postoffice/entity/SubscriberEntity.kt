package org.bagirov.postoffice.entity

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import java.util.*

@Entity
@Table(name="subscribers")
data class SubscriberEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

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
    var subscriptions: MutableSet<SubscriptionEntity>? = null,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    var user: UserEntity

){
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as SubscriberEntity

        return id != null && id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  id = $id   ,   district = $district   ,   street = $street   ,   building = $building   ,   subAddress = $subAddress   ,   user = $user )"
    }

}
