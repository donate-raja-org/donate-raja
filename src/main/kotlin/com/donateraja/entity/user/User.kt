package com.donateraja.entity.user

import com.donateraja.entity.constants.Gender
import com.donateraja.entity.constants.UserStatus
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@JsonIgnoreProperties("addresses", "roles")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    var email: String = "",

    @Column(name = "phone_number", unique = true)
    var phoneNumber: String? = null,

    @Column(nullable = false)
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotNull(message = "Password cannot be null")
    private var password: String, // Made private for security

    @Column(unique = true, nullable = false)
    @Size(min = 3, message = "Username must be at least 3 characters long")
    @NotNull(message = "Username cannot be null")
    private var username: String,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "profile_picture")
    var profilePicture: String? = null,

    @Column(name = "user_bio")
    var userBio: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    var gender: Gender = Gender.PREFER_NOT_TO_SAY,

    @Column(name = "dob")
    var dob: LocalDate? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVE,

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
    var lastLoginAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val addresses: MutableSet<Address> = mutableSetOf(),

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val roles: MutableSet<UserRole> = mutableSetOf()
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { GrantedAuthority { it.role.name } }.toMutableList()

    override fun getPassword(): String = password // Expose for Spring Security authentication

    override fun getUsername(): String = email // Spring Security uses email for authentication

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = status == UserStatus.ACTIVE

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = status == UserStatus.ACTIVE

    fun updatePassword(newPassword: String) {
        this.password = newPassword
    }

    // Prevent infinite recursion in equals/hashCode
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
    constructor() : this(
        0, "", "", "", "",
        null, null, null, "",
        Gender.PREFER_NOT_TO_SAY, LocalDate.now(), UserStatus.ACTIVE,
        false, false, null,
        null, LocalDateTime.now(), LocalDateTime.now(), null
    )
}
