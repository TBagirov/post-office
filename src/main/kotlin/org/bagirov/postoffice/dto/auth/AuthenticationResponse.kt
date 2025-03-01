package org.bagirov.postoffice.dto.auth

import java.util.*

data class AuthenticationResponse(
    var id: UUID,
    var username: String,
    var accessToken: String,
    var refreshToken: String
)
