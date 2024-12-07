package org.bagirov.postoffice.dto.response.ReportResponse.projection

import java.util.*

interface ReportPublicationProjection {
    val publicationId: UUID?
    val title: String
    val type: String?
    val price: Int
    val countSubscriber: Int
}