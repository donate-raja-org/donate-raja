import com.donateraja.entity.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "item_history")
class ItemHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemId: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    lateinit var title: String
    lateinit var description: String
    lateinit var condition: String
    lateinit var category: String
    var imageUrl: String? = null
    lateinit var itemType: String
    var pricePerDay: BigDecimal = BigDecimal.ZERO
    lateinit var availableFrom: LocalDateTime
    lateinit var availableTo: LocalDateTime
    lateinit var status: String
    var createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime = LocalDateTime.now()

    // No-args constructor (Required for JPA)
    constructor()

    // All-args constructor
    constructor(
        itemId: Long?,
        user: User,
        title: String,
        description: String,
        condition: String,
        category: String,
        imageUrl: String?,
        itemType: String,
        pricePerDay: BigDecimal,
        availableFrom: LocalDateTime,
        availableTo: LocalDateTime,
        status: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ) {
        this.itemId = itemId
        this.user = user
        this.title = title
        this.description = description
        this.condition = condition
        this.category = category
        this.imageUrl = imageUrl
        this.itemType = itemType
        this.pricePerDay = pricePerDay
        this.availableFrom = availableFrom
        this.availableTo = availableTo
        this.status = status
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}
