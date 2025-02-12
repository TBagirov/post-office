package org.bagirov.postoffice.dto.auth

data class RegistrationRequest (
    val username: String,
    val name: String,
    val password: String,
    val email: String
)
