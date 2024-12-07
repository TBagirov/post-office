package org.bagirov.postoffice.dto.request.update

import java.util.*

data class SubscriberUpdateRequest (
    val id: UUID,
    val name: String,
    val surname: String,
    val patronymic: String,
    val building: String,
    val subAddress: String?,
    val streetId: UUID,
    val districtId: UUID
)