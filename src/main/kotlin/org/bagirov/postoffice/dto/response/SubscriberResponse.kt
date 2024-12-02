package org.bagirov.postoffice.dto.response

import java.util.*

data class SubscriberResponse (
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String,
    val building: String,
    val subAddress: String,
    val street: String,
    val regionName: String,
    val postmanName: String
)