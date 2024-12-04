package org.bagirov.postoffice.dto.response.ReportResponse

import java.time.LocalDateTime

data class ReportSubscriptionResponse (
    val title: String,
    val type: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val price: Int
)