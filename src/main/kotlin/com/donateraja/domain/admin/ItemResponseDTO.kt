package com.donateraja.domain.admin

import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.Condition
import com.donateraja.entity.constants.DonationOrRent
import com.donateraja.entity.constants.ItemStatus
import com.donateraja.entity.item.Item
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
    @JsonProperty("images") val images: List<String> = emptyList(),
    @JsonProperty("tags") val tags: List<String> = emptyList()
) {
    companion object {
        fun fromEntity(item: Item, images: List<String>, tags: List<String>): ItemResponseDTO = ItemResponseDTO(
            id = item.id,
            itemName = item.itemName,
            description = item.description,
            condition = item.condition,
            price = item.price,
            location = item.location,
            pincode = item.pincode,
            category = item.category,
            donationOrRent = item.donationOrRent,
            status = item.status,
            createdAt = item.createdAt,
            updatedAt = item.updatedAt,
            images = images,
            tags = tags
        )
    }
}
