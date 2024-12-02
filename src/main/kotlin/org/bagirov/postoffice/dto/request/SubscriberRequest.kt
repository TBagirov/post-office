package org.bagirov.postoffice.dto.request

data class SubscriberRequest (
    val name: String,
    val surname: String,
    val patronymic: String,
    val building: String,
    val subAddress: String?,
    val street: String
)