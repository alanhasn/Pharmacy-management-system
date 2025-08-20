package domain.model

/**
 * Data class for prescription item
 */
data class PrescriptionItem(
    val medicine: Medicine,
    val quantity: Int
)