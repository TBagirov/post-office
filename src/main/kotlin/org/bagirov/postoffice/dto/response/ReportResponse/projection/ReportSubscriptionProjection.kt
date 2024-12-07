package org.bagirov.postoffice.dto.response.ReportResponse.projection

import java.time.LocalDateTime
import java.util.*

interface ReportSubscriptionProjection {
    val subscriptionId: UUID
    val subscriberId: UUID?
    val publicationId: UUID?
    val fioSubscriber: String
    val titlePublication: String?
    val startDateSubscription: LocalDateTime
    val endDateSubscription: LocalDateTime
    val statusSubscription: String
}
