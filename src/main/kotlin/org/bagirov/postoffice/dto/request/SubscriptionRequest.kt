package org.bagirov.postoffice.dto.request

import java.util.*

data class SubscriptionRequest (
    val publicationId: UUID,
    val duration: Int
)