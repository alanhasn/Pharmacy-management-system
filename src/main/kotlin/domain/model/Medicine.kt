package domain.model
import java.time.Instant
import java.util.UUID

data class Medicine(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sku: String,
    val expiresAt: Instant?,
    val price: Double,
    val minAge: Int? = null,
    var quantity: Int,
    val requiresSpecialLicense: Boolean = false
)







