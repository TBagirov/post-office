package org.bagirov.postoffice.dto.response

import java.time.LocalDateTime
import java.util.*

data class SubscriptionResponse (
    val id: UUID,
    val subscriber: SubscriberResponse,
    val publication: PublicationResponse,
    val startDate: LocalDateTime,
    val duration: Int,
    val price: Int
)