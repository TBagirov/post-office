package org.bagirov.postoffice.dto.response

import java.util.*

data class PublicationResponse (
    val id: UUID,
    val index: String,
    val title: String,
    val publicationType: String?,
    val price: Int

)