package com.donateraja.model.user

import com.donateraja.entity.constants.Gender
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.Instant

data class UserProfileDto(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String?,
    val profilePicture: String?,
    val userBio: String,
    val gender: Gender,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val dob: Instant
)
