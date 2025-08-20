package domain.model

import java.time.Instant
import java.util.UUID

/**
 * Data class for prescription and add business rules for validation before saving the prescription
 * @param id
 * @param customer
 * @param pharmacist
 * @param items
 * @param createdAt
 *
 */
data class Prescription(
    val id: String = UUID.randomUUID().toString(),
    val customer: Customer,
    val pharmacist: Pharmacist,
    val items: List<PrescriptionItem>,
    val createdAt: Instant = Instant.now()
) {
    init {
        // A prescription must contain at least two medicines
        if (items.size < 2) {
            throw IllegalArgumentException("Prescription must contain at least two medicines.")
        }

        // Each quantity must be positive
        for (item in items) {
            if (item.quantity <= 0) {
                throw IllegalArgumentException("All quantities must be positive.")
            }
        }

        // Medicines in the same prescription must not be duplicated
        val seenIds = mutableSetOf<String>()
        for (item in items) {
            if (!seenIds.add(item.medicine.id)) {
                throw IllegalArgumentException("Duplicate medicines in the same prescription are not allowed.")
            }
        }

        // Expired medicines are not allowed
        for (item in items) {
            val expiry = item.medicine.expiresAt
            if (expiry != null && expiry.isBefore(Instant.now())) {
                throw IllegalArgumentException("Prescription contains expired medicine.")
            }
        }

        // Check minimum age requirement
        for (item in items) {
            val minAge = item.medicine.minAge
            if (minAge != null && customer.age() < minAge) {
                throw IllegalArgumentException("Customer does not meet the minimum age requirement.")
            }
        }

        // Check pharmacist’s license for special medicines
        for (item in items) {
            if (item.medicine.requiresSpecialLicense && !pharmacist.hasSpecialLicense) {
                throw IllegalArgumentException("Pharmacist is not authorized to dispense one or more medicines.")
            }
        }
    }
}
