package com.donateraja.domain.banner

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class BannerDTO(
    @JsonProperty("id") val id: Long?,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("image_url") val imageUrl: String?,
    @JsonProperty("start_date") val startDate: LocalDateTime,
    @JsonProperty("end_date") val endDate: LocalDateTime
)
