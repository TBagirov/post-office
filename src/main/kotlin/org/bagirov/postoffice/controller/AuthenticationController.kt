package org.bagirov.postoffice.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletResponse
import org.bagirov.postoffice.dto.auth.AuthenticationRequest
import org.bagirov.postoffice.dto.auth.AuthenticationResponse
import org.bagirov.postoffice.dto.auth.RegistrationRequest
import org.bagirov.postoffice.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/authentication")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/authorization")
    @Operation(summary = "Авторизация пользователя")
    fun authorization(@RequestBody request: AuthenticationRequest, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.authorization(request, response))
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя")
    fun registration(@RequestBody request: RegistrationRequest, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.registration(request, response))
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход пользователя с сайта")
    fun logout(@CookieValue(value = "refreshToken") token: String,
               response: HttpServletResponse): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(authenticationService.logout(token, response))
    }

    @GetMapping("/refresh")
    @Operation(summary = "Обновление токена")
    fun refresh(@CookieValue(value = "refreshToken") token: String, response: HttpServletResponse): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.refresh(token, response))
    }

    @ExceptionHandler
    fun handleException(ex: Exception) : ResponseEntity<*> {
        return ResponseEntity.badRequest().body(mapOf("error" to "${ex.message}"))
    }

}