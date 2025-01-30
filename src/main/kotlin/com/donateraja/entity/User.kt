package com.donateraja.entity

import com.donateraja.entity.constants.Role
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    @field:Email(message = "Email should be valid")
    @field:NotNull(message = "Email cannot be null")
    var email: String,

    @Column(nullable = false)
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    @field:NotNull(message = "Password cannot be null")
    @get:JvmName("getUserPassword") // Renamed JVM getter
    var password: String,

    @Column(unique = true, nullable = false)
    @field:Size(min = 3, message = "Username must be at least 3 characters long")
    @field:NotNull(message = "Username cannot be null")
    @get:JvmName("getUserUsername") // Renamed JVM getter
    var username: String,

    @Column(name = "first_name")
    @field:NotNull(message = "First name cannot be null")
    var firstName: String,

    @Column(name = "last_name")
    @field:NotNull(message = "Last name cannot be null")
    var lastName: String,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "profile_picture")
    var profilePicture: String? = null,

    @Column
    var address: String? = null,

    @Column(name = "pin_code")
    var pinCode: String? = null,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var roles: Set<Role> = setOf(Role.ROLE_USER),

    @Column
    var status: String = "ACTIVE",

    @Column(name = "is_email_verified")
    var isEmailVerified: Boolean = false,

    @Column(name = "is_phone_verified")
    var isPhoneVerified: Boolean = false
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }

    override fun getPassword(): String = password // Override UserDetails method

    override fun getUsername(): String = username // Override UserDetails method

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = status == "ACTIVE"
}