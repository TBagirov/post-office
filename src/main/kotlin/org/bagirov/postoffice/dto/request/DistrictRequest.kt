package org.bagirov.postoffice.dto.request

import java.util.*

data class DistrictRequest(
    val postmanId: UUID,
    val regionId: UUID
)