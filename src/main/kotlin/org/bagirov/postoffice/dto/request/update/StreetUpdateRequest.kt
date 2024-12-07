package org.bagirov.postoffice.dto.request.update

import java.util.*

class StreetUpdateRequest (
    val id: UUID,
    val name: String,
    val regionId: UUID
)