package com.donateraja.entity.user

import com.donateraja.entity.constants.Role
import com.donateraja.entity.constants.Status
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime


@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    var id: Long = 0,

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    var email: String,

    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotNull(message = "Password cannot be null")
    @get:JvmName("getUserPassword") // Avoids clash
    var password: String,

    @Column(unique = true, nullable = false)
    @Size(min = 3, message = "Username must be at least 3 characters long")
    @NotNull(message = "Username cannot be null")
    @get:JvmName("getUserName")
    var username: String,

    @Column(name = "first_name")
    @NotNull(message = "First name cannot be null")
    var firstName: String,

    @Column(name = "last_name")
    @NotNull(message = "Last name cannot be null")
    var lastName: String,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "profile_picture")
    var profilePicture: String? = null,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var roles: Set<Role> = setOf(Role.ROLE_USER),

    @Enumerated(EnumType.STRING)
    @Column
    var status: Status = Status.ACTIVE,

    @Column(name = "is_email_verified")
    var isEmailVerified: Boolean = false,

    @Column(name = "is_phone_verified")
    var isPhoneVerified: Boolean = false,

    @Column(name = "verification_code")
    var verificationCode: String? = null,

    @Column(name = "verification_expires")
    var verificationExpires: LocalDateTime? = null,

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_login_at")
    var lastLoginAt: LocalDateTime? = null
) : UserDetails {
    constructor() : this(
        id = 0,
        email = "",
        password = "",
        username = "",
        firstName = "",
        lastName = "",
        phoneNumber = null,
        profilePicture = null,
        roles = setOf(Role.ROLE_USER),
        status = Status.ACTIVE,
        isEmailVerified = false,
        isPhoneVerified = false,
        verificationCode = null,
        verificationExpires = null,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        lastLoginAt = null
    )

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.name) }
    }

    override fun getPassword(): String = password  // No clash due to @get:JvmName above

    override fun getUsername(): String = this.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = status == Status.ACTIVE
}
