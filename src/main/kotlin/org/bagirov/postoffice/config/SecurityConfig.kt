package org.bagirov.postoffice.config

import org.bagirov.postoffice.exception.CustomAccessDeniedHandler
import org.bagirov.postoffice.service.JwtService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { obj: AbstractHttpConfigurer<*, *> -> obj.disable() }
            .addFilterBefore(JwtAuthenticationFilter(userDetailsService, jwtService), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests { authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .requestMatchers(
                        "/api/authentication/**",
                    )
                    .permitAll()
                    .requestMatchers("/api/role/**").hasAuthority("ADMIN")
                    .requestMatchers("/api/postman/**").hasAuthority("ADMIN")
                    .requestMatchers("/api/report/**").hasAuthority("ADMIN")
                    .requestMatchers("/api/subscriber/create").hasAuthority("GUEST")
                    .requestMatchers("/api/subscriber/update").hasAuthority("SUBSCRIBER")
                    .requestMatchers("/api/subscriber/delete").hasAuthority("SUBSCRIBER")
                    .anyRequest()
                    .authenticated()
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement { httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }.exceptionHandling { exceptions ->
                exceptions.accessDeniedHandler(customAccessDeniedHandler) // Используем кастомный обработчик
            }

        return http.build()
    }
}