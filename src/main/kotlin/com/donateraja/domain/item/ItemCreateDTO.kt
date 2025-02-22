package com.donateraja.domain.item

import com.donateraja.entity.constants.Category
import com.donateraja.entity.constants.Condition
import com.donateraja.entity.constants.DonationOrRent
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

class ItemCreateDTO {

    @JsonProperty("item_name")
    @NotBlank(message = "item_name cannot be null or blank")
    @Size(max = 100)
    var itemName: String? = null

    @JsonProperty("description")
    @NotBlank(message = "description cannot be null or blank")
    @Size(max = 1000)
    var description: String? = null

    @JsonProperty("condition")
    @NotNull(message = "condition cannot be null")
    var condition: Condition? = null

    @JsonProperty("price")
    @Positive(message = "price must be positive")
    var price: Double? = null

    @JsonProperty("location")
    @NotBlank(message = "location cannot be null or blank")
    var location: String? = null

    @JsonProperty("pincode")
    @NotBlank(message = "pincode cannot be null or blank")
    var pincode: String? = null

    @JsonProperty("category")
    @NotNull(message = "category cannot be null")
    var category: Category? = null

    @JsonProperty("donation_or_rent")
    @NotNull(message = "donation_or_rent cannot be null")
    var donationOrRent: DonationOrRent? = null

    @JsonProperty("user_id")
    @NotNull(message = "user cannot be null")
    var userId: Long? = null

    @JsonProperty("image_urls")
    var imageUrls: List<String> = emptyList()

    @JsonProperty("tags")
    var tags: List<String> = emptyList()

    constructor()

    constructor(
        itemName: String,
        description: String,
        condition: Condition,
        price: Double,
        location: String,
        pincode: String,
        category: Category,
        donationOrRent: DonationOrRent,
        userId: Long
    ) {
        this.itemName = itemName
        this.description = description
        this.condition = condition
        this.price = price
        this.location = location
        this.pincode = pincode
        this.category = category
        this.donationOrRent = donationOrRent
        this.userId = userId
        this.imageUrls = imageUrls
        this.tags = tags
    }
}
