package org.bagirov.postoffice.dto.response

import java.util.*

data class RegionResponse (
    val id: UUID,
    val name: String,
    val streets: List<String?>?,
    val postmans: List<String?>?
)