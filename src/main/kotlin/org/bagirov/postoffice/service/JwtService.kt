package org.bagirov.postoffice.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import lombok.RequiredArgsConstructor
import org.bagirov.postoffice.entity.UserEntity
import org.bagirov.postoffice.service.props.JwtProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey


@Service
@RequiredArgsConstructor
class JwtService(
    val jwtProperties: JwtProperties,
    val userDetailsService: UserDetailsService,
//    val userService: UserService,
){
    lateinit var key: SecretKey

    @PostConstruct
    fun init(){
        key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    }

    fun createAccessToken(
        user: UserEntity
    ): String {
        val claims = Jwts.claims()
            .subject(user.username)
            .add("id", user.id)
            .add("role", user.role.toString())
            .build()
        val validity = Instant.now()
            .plus(jwtProperties.accessExpiration, ChronoUnit.HOURS)
        return Jwts.builder()
            .claims(claims)
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()
    }



    fun createRefreshToken(
        user: UserEntity
    ): String {
        val claims = Jwts.claims()
            .subject(user.username)
            .add("id", user.id)
            .build()
        val validity = Instant.now()
            .plus(jwtProperties.refreshExpiration, ChronoUnit.DAYS)
        return Jwts.builder()
            .claims(claims)
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()
    }

//    fun refreshUserTokens(
//        refreshToken: String?
//    ): AuthenticationResponse {
//
//        if (!isValid(refreshToken)) {
//            throw AccessDeniedException("не валидный токен")
//        }
//        val userId = getId(refreshToken)
//        val user: UserEntity? = userService.getById(UUID.fromString(userId))
//
//        val authenticationResponse: AuthenticationResponse = AuthenticationResponse(
//            id = UUID.fromString(userId),
//            username = user!!.username,
//            accessToken = createAccessToken(user),
//            refreshToken = createRefreshToken(user),
//        )
//
//        return authenticationResponse
//    }

    fun isValid(
        token: String?,
        userDetails: UserDetails
    ): Boolean {
        val username = getUsername(token!!)
        val claims = Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
        return claims.payload
            .expiration
            .after(Date()) && username == userDetails.username
    }

    fun getId(
        token: String?
    ): String {
        return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .get("id", String::class.java)
    }

    fun getUsername(
        token: String
    ): String {
        return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun getAuthentication(
        token: String
    ): Authentication {
        val username = getUsername(token)
        val userDetails = userDetailsService.loadUserByUsername(
            username
        )
        return UsernamePasswordAuthenticationToken(
            userDetails,
            "",
            userDetails.authorities
        )
    }


}