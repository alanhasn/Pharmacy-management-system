package domain.model

import java.time.Instant
import java.util.UUID
import kotlin.system.exitProcess

/**
 * Data class for prescription and add business rules for validation before saving the prescription
 * @param id
 * @param customer
 * @param pharmacist
 * @param items
 * @param createdAt
 */
data class Prescription(
    val id: String = UUID.randomUUID().toString(),
    val customer: Customer,
    val pharmacist: Pharmacist,
    val items: List<PrescriptionItem>,
    val createdAt: Instant = Instant.now()
)
