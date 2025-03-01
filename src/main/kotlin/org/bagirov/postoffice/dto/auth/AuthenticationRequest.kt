package org.bagirov.postoffice.dto.auth

data class AuthenticationRequest(
    var username: String,
    var password: String
)
