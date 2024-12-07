package org.bagirov.postoffice.dto.response.ReportResponse

import java.time.LocalDateTime
import java.util.*

data class ReportSubscriptionResponse (
    val subscriptionId: UUID,
    val subscriberId: UUID?,
    val publicationId: UUID?,
    val fioSubscriber: String,
    val titlePublication: String?,
    val startDateSubscription: LocalDateTime,
    val endDateSubscription: LocalDateTime,
    val statusSubscription: String
)