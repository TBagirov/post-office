package org.bagirov.postoffice.dto.response.ReportResponse

data class ReportPublicationResponse (
    val title: String,
    val type: String,
    val price: Int,
    val countSubscriber: Int
)