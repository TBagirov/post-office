package org.bagirov.postoffice.dto.request

import java.util.*

data class SubscriptionRequest (
    val subscriberId: UUID,
    val publicationId: UUID,
    val duration: Int
)