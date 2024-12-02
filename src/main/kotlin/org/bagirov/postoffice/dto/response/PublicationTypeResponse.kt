package org.bagirov.postoffice.dto.response

import java.util.*

data class PublicationTypeResponse (
    val id: UUID,
    val type: String,
    val publications: List<PublicationResponse>?
)