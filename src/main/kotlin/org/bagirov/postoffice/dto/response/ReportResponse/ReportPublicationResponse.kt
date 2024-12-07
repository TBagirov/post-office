package org.bagirov.postoffice.dto.response.ReportResponse

import java.util.*

data class ReportPublicationResponse (
    val publicationId: UUID?,
    val title: String,
    val type: String?,
    val price: Int,
    val countSubscriber: Int
)