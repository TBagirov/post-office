package org.bagirov.postoffice.dto.request.update

import java.time.LocalDateTime
import java.util.*

data class SubscriptionUpdateRequest (
    val id: UUID,
    val publicationId: UUID,
    val startDate: LocalDateTime,
    val duration: Int
)