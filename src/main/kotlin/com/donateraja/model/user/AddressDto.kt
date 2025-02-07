import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class AddressDto(
    @JsonProperty("pin_code")
    @NotBlank(message = "Pin code is required")
    @Size(max = 20, message = "Pin code must not exceed 20 characters")
    val pinCode: String,

    @JsonProperty("street")
    @NotBlank(message = "Street is required")
    val street: String? = null,

    @JsonProperty("city")
    @NotBlank(message = "City is required")
    val city: String? = null,

    @JsonProperty("state")
    @NotBlank(message = "State is required")
    val state: String? = null
)
