package org.bagirov.postoffice.service

import jakarta.transaction.Transactional
import org.bagirov.postoffice.entity.UserEntity
import org.bagirov.postoffice.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.webjars.NotFoundException
import java.util.*


@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserService {

    override fun getById(
        id: UUID
    ): UserEntity {
        return userRepository.findById(id)
            .orElseThrow( {
                NotFoundException("Not found user with id: $id")
            })
    }

    override fun getByUsername(
        username: String
    ): UserEntity {
        return userRepository.findByUsername(username)
            .orElseThrow( {
                NotFoundException(
                    "User not found with name: $username."
                )
            })
    }

    @Transactional
    override fun update(
        user: UserEntity
    ): UserEntity {
        val existing: UserEntity = getById(user.id!!)
        existing.name = user.name
        existing.username = user.username
        existing.password = passwordEncoder.encode(user.password)

        //user.setUsername(user.getUsername())
       // user.setPassword(passwordEncoder.encode(user.getPassword()))

//        userRepository.save<UserEntity>(users)
        userRepository.save<UserEntity>(existing)
//        return user
        return existing
    }

    @Transactional
    override fun create(
        user: UserEntity
    ): UserEntity {
        check(!userRepository.findByUsername(user.getUsername()).isPresent) { "User already exists." }

        user.password = passwordEncoder.encode(user.getPassword())
        userRepository.save<UserEntity>(user)
        return user
    }


    @Transactional
    //@CacheEvict(value = ["UserService::getById"], key = "#id")
    override fun delete(
        id: UUID
    ) {
        userRepository.deleteById(id)
    }

}