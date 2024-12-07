package org.bagirov.postoffice.dto.request.update

import java.util.*

data class DistrictUpdateRequest(
    val id: UUID,
    val postmanId: UUID,
    val regionId: UUID
)