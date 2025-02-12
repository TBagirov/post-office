package org.bagirov.postoffice.entity

import jakarta.persistence.*
import lombok.Getter
import lombok.Setter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


@Entity
@Table(name = "users")
@Getter
@Setter
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "username", nullable = false)
    var username: String,

    @Column(name = "email", nullable = true)
    var email: String? = null,

    @Column(name = "password", nullable = false)
    var password: String,

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    var role: RoleEntity,

    @OneToMany(mappedBy = "user")
    var user: MutableList<RefreshTokenEntity>? = mutableListOf()

) : UserDetails{

    override fun getAuthorities(): Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}
