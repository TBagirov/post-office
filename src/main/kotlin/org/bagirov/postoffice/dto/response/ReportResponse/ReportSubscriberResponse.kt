package org.bagirov.postoffice.dto.response.ReportResponse

import java.time.LocalDateTime

data class ReportSubscriberResponse (
    val fioSubscriber: String,
    val titlePublication: String,
    val startDateSubscription: LocalDateTime,
    val endDateSubscription: LocalDateTime,
    val statusSubscription: String
)