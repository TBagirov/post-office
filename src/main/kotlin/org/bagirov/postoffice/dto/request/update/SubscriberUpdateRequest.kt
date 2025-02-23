package org.bagirov.postoffice.dto.request.update

import java.util.*

data class SubscriberUpdateRequest (
    val building: String,
    val subAddress: String?,
    val streetId: UUID,
    val districtId: UUID
)