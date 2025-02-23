package com.donateraja.domain.admin

import com.donateraja.entity.constants.Gender
import com.donateraja.entity.constants.UserStatus
import com.donateraja.entity.user.User
import com.donateraja.entity.user.UserRole
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class UserProfileDto(
    @JsonProperty("id") val id: Long,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("phone_number") val phoneNumber: String?,
    @JsonProperty("role") val role: Set<UserRole>,
    @JsonProperty("status") val status: UserStatus,
    @JsonProperty("profile_picture") val profilePicture: String?,
    @JsonProperty("user_bio") val userBio: String?,
    @JsonProperty("gender") val gender: Gender?,
    @JsonProperty("dob")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val dob: LocalDate?
) {
    companion object {
        fun fromEntity(user: User): UserProfileDto = UserProfileDto(
            id = user.id!!,
            firstName = user.firstName!!,
            lastName = user.lastName!!,
            email = user.email,
            phoneNumber = user.phoneNumber,
            role = user.roles,
            status = user.status,
            profilePicture = user.profilePicture ?: "",
            userBio = user.userBio ?: "",
            gender = user.gender ?: Gender.PREFER_NOT_TO_SAY,
            dob = user.dob ?: LocalDate.of(1900, 1, 1)
        )
    }
}
