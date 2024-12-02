package org.bagirov.postoffice.dto.request


data class PublicationRequest(
    val index: String,
    val title: String,
    val publicationType: String,
    val price: Int
)