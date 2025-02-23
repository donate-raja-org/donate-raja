package com.donateraja.domain.reviews

import com.fasterxml.jackson.annotation.JsonProperty

data class ItemReviewRequestDTO(
    @JsonProperty("item_id") val itemId: Long,
    @JsonProperty("rating") val rating: Int,
    @JsonProperty("comment") val comment: String?
)
