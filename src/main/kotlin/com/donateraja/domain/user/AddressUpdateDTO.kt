package com.donateraja.domain.user

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

class AddressUpdateDTO {
    @JsonProperty("street")
    @NotBlank(message = "street cannot be blank")
    var street: String? = null

    @JsonProperty("city")
    @NotBlank(message = "city cannot be blank")
    var city: String? = null

    @JsonProperty("state")
    @NotBlank(message = "state cannot be blank")
    var state: String? = null

    @JsonProperty("pincode")
    @NotBlank(message = "pincode cannot be blank")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode format")
    var pincode: String? = null

    @JsonProperty("country")
    @NotBlank(message = "country cannot be blank")
    var country: String? = null
}
