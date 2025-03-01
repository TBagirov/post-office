package org.bagirov.postoffice.dto.request

import org.bagirov.postoffice.entity.UserEntity

data class SubscriberRequest (
    val building: String,
    val subAddress: String?,
    val street: String
)