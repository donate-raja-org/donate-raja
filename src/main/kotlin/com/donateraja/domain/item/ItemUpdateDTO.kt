package com.donateraja.domain.item

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

class ItemUpdateDTO {

    @JsonProperty("item_name")
    @Size(max = 100)
    var itemName: String? = null

    @JsonProperty("description")
    @Size(max = 1000)
    var description: String? = null

    @JsonProperty("price")
    @Positive(message = "price must be positive")
    var price: Double? = null

    constructor()

    constructor(
        itemName: String?,
        description: String?,
        price: Double?
    ) {
        this.itemName = itemName
        this.description = description
        this.price = price
    }
}
