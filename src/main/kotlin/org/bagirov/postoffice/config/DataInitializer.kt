package org.bagirov.postoffice.config

import org.bagirov.postoffice.entity.PostmanEntity
import org.bagirov.postoffice.entity.RoleEntity
import org.bagirov.postoffice.entity.UserEntity
import org.bagirov.postoffice.repository.PostmanRepository
import org.bagirov.postoffice.repository.RoleRepository
import org.bagirov.postoffice.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DataInitializer(private val roleRepository: RoleRepository,
                      private val userRepository: UserRepository,
                      private val passwordEncoder: PasswordEncoder,

                      ) : CommandLineRunner {

    override fun run(vararg args: String?) {

        val roleAll = roleRepository.findAll()
        roleAll.find { it.name == "GUEST" } ?: roleRepository.save(RoleEntity(name = "GUEST"))

        val userAll = userRepository.findAll()
        val id = userAll.find { it.role.name == "ADMIN" }
        if (id == null) {

            val roleAdmin = roleAll.find{it.name == "ADMIN"} ?: roleRepository.save(RoleEntity(name = "ADMIN"))

            val user = UserEntity(
                username = "admin",
                password = passwordEncoder.encode("admin"),
                role = roleAdmin,
                name = "adminName",
                surname = "adminSurname",
                patronymic = "adminPatronymic",
                email = "admin@example.com",
                phone = "+1234567890",
                createdAt = LocalDateTime.now()
            )
            userRepository.save(user)

            println("Initial data has been inserted into the database.")
        }



        println("The user with the admin role already exists.")
    }
}
