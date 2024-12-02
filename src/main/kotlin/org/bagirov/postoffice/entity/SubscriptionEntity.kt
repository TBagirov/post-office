package org.bagirov.postoffice.entity

import jakarta.persistence.*
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
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubscriptionEntity

        if (id != other.id) return false
        if (publication != other.publication) return false
        if (startDate != other.startDate) return false
        if (duration != other.duration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (publication?.hashCode() ?: 0)
        result = 31 * result + startDate.hashCode()
        result = 31 * result + duration
        return result
    }
}
