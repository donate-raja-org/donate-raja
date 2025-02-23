package com.donateraja.domain.item

import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.Condition
import com.donateraja.entity.constants.DonationOrRent
import com.donateraja.entity.constants.ItemStatus
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ItemResponseDTO(
    @JsonProperty("id") val id: Long,
    @JsonProperty("item_name") val itemName: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("condition") val condition: Condition,
    @JsonProperty("price") val price: Double,
    @JsonProperty("location") val location: String,
    @JsonProperty("pincode") val pincode: String,
    @JsonProperty("category") val category: Category,
    @JsonProperty("donation_or_rent") val donationOrRent: DonationOrRent,
    @JsonProperty("status") val status: ItemStatus,
    @JsonProperty("created_at") val createdAt: LocalDateTime,
    @JsonProperty("updated_at") val updatedAt: LocalDateTime,
    @JsonProperty("image_urls") val imageUrls: List<String>,
    @JsonProperty("tags") val tags: List<String>
) {
    // Secondary constructor for compatibility (if needed)
    constructor(
        id: Long,
        itemName: String,
        description: String,
        condition: Condition,
        price: Double,
        location: String,
        pincode: String,
        category: Category,
        donationOrRent: DonationOrRent,
        status: ItemStatus,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ) : this(
        id, itemName, description, condition, price, location, pincode,
        category, donationOrRent, status, createdAt, updatedAt, emptyList(), emptyList()
    )
}
