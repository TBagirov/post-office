package org.bagirov.postoffice.dto.response.ReportResponse

import java.time.LocalDateTime
import java.util.*

data class ReportSubscriptionByIdSubscriberResponse (
    val subscriptionId: UUID,
    val publicationId: UUID?,
    val title: String?,
    val type: String?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val price: Int?
)