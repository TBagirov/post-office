package org.bagirov.postoffice.dto.request.update

import java.util.*


data class PublicationUpdateRequest(
    val id: UUID,
    val index: String,
    val title: String,
    val publicationTypeId: UUID,
    val price: Int
)