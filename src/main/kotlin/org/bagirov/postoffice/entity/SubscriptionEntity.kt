package org.bagirov.postoffice.entity

import jakarta.persistence.*
import org.hibernate.proxy.HibernateProxy
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name="subscriptions")
data class SubscriptionEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    var subscriber: SubscriberEntity? = null,

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    var publication: PublicationEntity? = null,

    @Column(name="start_date", nullable = false)
    var startDate: LocalDateTime,

    @Column(name="duration", nullable = false)
    var duration: Int
){
    final override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        val oEffectiveClass =
            if (other is HibernateProxy) other.hibernateLazyInitializer.persistentClass else other.javaClass
        val thisEffectiveClass =
            if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        other as SubscriptionEntity

        return id != null && id == other.id
    }

    final override fun hashCode(): Int =
        if (this is HibernateProxy) this.hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(  id = $id   ,   subscriber = $subscriber   ,   publication = $publication   ,   startDate = $startDate   ,   duration = $duration )"
    }

}
