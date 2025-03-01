package org.bagirov.postoffice.entity

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
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
    @JoinColumn(name = "publication_type_id")
    var publicationType: PublicationTypeEntity?,

    @Column(name="price", nullable = false)
    var price: Int,

    @OneToMany(mappedBy="publication")
    var subscriptions: MutableList<SubscriptionEntity>? = null
) {
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as PublicationEntity

        return id != null && id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  id = $id   ,   index = $index   ,   title = $title   ,   publicationType = $publicationType   ,   price = $price )"
    }
}

