package domain.model

/**
 * Data class for inventory item
 */
data class InventoryItem(
    val medicine: Medicine,
    var quantity: Int
)
