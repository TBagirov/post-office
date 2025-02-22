package org.bagirov.postoffice.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "users")
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name="name", nullable = false)
    var name: String,

    @Column(name="surname", nullable = false)
    var surname: String,

    @Column(name="patronymic", nullable = false)
    var patronymic: String,

    @Column(name = "username", nullable = false)
    private var username: String,

    @Column(name = "password", nullable = false)
    private var password: String,

    @Column(name = "email", nullable = true)
    var email: String,

    @Column(name = "phone", nullable = false)
    var phone: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    var role: RoleEntity,

    @OneToMany(mappedBy = "user")
    var tokens: MutableList<RefreshTokenEntity>? = mutableListOf(),

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var postman: PostmanEntity? = null,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.REMOVE])
    var subscriber: SubscriberEntity? = null

) : UserDetails{

    override fun getAuthorities(): Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
    override fun toString(): String {
        return "UserEntity(id=$id, name='$name', username='$username', email='$email', password='$password', role=$role, tokens=$tokens)"
    }

    fun getFio() = listOfNotNull(surname, name, patronymic)
        .joinToString(" ")

}
