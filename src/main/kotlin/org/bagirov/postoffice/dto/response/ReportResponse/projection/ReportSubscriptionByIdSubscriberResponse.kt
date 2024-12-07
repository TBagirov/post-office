package org.bagirov.postoffice.dto.response.ReportResponse.projection

import java.time.LocalDateTime
import java.util.UUID

interface ReportSubscriptionByIdSubscriberProjection {
    val subscriptionId: UUID
    val publicationId: UUID?
    val title: String?
    val type: String?
    val startDate: LocalDateTime
    val endDate: LocalDateTime
    val price: Int?
}