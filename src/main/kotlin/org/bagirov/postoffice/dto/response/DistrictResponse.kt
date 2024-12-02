package org.bagirov.postoffice.dto.response

import java.util.*

data class DistrictResponse (
    val id: UUID,
    val postmanName: String,
    val regionName: String
)