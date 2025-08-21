package application.service

import application.ports.PrescriptionRepository
import domain.model.Medicine
import domain.model.Prescription
import domain.model.PrescriptionItem
import domain.model.PrescriptionStatus
import kotlinx.coroutines.delay
import java.time.Instant

class PrescriptionService(
    private val prescriptionRepository: PrescriptionRepository,
    private val inventoryRepository: InventoryService,
    private val customerService: CustomerService,
    private val pharmacistService: PharmacistService
) {

    /**
     * Create a new prescription:
     * - Load customer & pharmacist
     * - Validate medicine availability
     * - Reduce stock
     * - Save prescription
     */
    suspend fun createPrescription(
        customerId: String,
        pharmacistId: String,
        items: List<PrescriptionItem>
    ): PrescriptionStatus {
        // 0. load customer & pharmacist
        val customer = customerService.getCustomerById(customerId)
            ?: return PrescriptionStatus.Rejected("Customer not found")

        val pharmacist = pharmacistService.getPharmacistById(pharmacistId)
            ?: return PrescriptionStatus.Rejected("Pharmacist not found")

        val prescription = Prescription(
            customer = customer,
            pharmacist = pharmacist,
            items = items
        )

        // 1. check availability
        for (item in prescription.items) {
            val available = inventoryRepository.isAvailable(item.medicine.id, item.quantity)
            delay(1000)
            if (!available) {
                return PrescriptionStatus.Rejected(
                    "Medicine ${item.medicine.name} not available in required quantity"
                )
            }
        }

        // 2. reduce stock
        for (item in prescription.items) {
            val success = inventoryRepository.reduceStock(item.medicine.id, item.quantity)
            if (!success) {
                delay(1000)
                return PrescriptionStatus.Rejected(
                    "Failed to reduce stock for ${item.medicine.name}"
                )
            }
        }

        // Each quantity must be positive
        for (item in items) {
            if (item.quantity <= 0) {
                return PrescriptionStatus.Rejected("All quantities must be positive.")
            }
        }

        // Medicines in the same prescription must not be duplicated
        val seenIds = mutableSetOf<String>()
        for (item in items) {
            if (!seenIds.add(item.medicine.id)) {
                return PrescriptionStatus.Rejected("Duplicate medicines in the same prescription are not allowed.")
            }
        }

        // Expired medicines are not allowed
        for (item in items) {
            val expiry = item.medicine.expiresAt
            if (expiry != null && expiry.isBefore(Instant.now())) {
                return PrescriptionStatus.Rejected("Prescription contains expired medicine.")
            }
        }

        // Check minimum age requirement
        for (item in items) {
            val minAge = item.medicine.minAge
            if (minAge != null && customer.age() < minAge) {
                return PrescriptionStatus.Rejected("Customer does not meet the minimum age requirement.")
            }
        }

        // Check pharmacist’s license for special medicines
        for (item in items) {
            if (item.medicine.requiresSpecialLicense && !pharmacist.hasSpecialLicense) {
                return PrescriptionStatus.Rejected("Pharmacist is not authorized to dispense one or more medicines.")
            }
        }

        // 3. save prescription
        delay(1000)
        prescriptionRepository.save(prescription)
        return PrescriptionStatus.Approved
    }

    /**
     * Retrieve prescription by ID
     */
    suspend fun getPrescriptionById(id: String): Prescription? {
        delay(1000)
        return prescriptionRepository.findById(id)
    }

    /**
     * Get all prescriptions for a given customer
     */
    suspend fun getPrescriptionsByCustomer(customerId: String): List<Prescription> {
        delay(1000)
        return prescriptionRepository.findByCustomer(customerId)
    }

    /**
     * Get all prescriptions for a given pharmacist
     */
    suspend fun getPrescriptionsByPharmacist(pharmacistId: String): List<Prescription> {
        delay(1000)
        return prescriptionRepository.findByPharmacist(pharmacistId)
    }

    /**
     * Get all prescriptions
     */
    suspend fun getAllPrescriptions(): List<Prescription> {
        delay(1000)
        return prescriptionRepository.findAll()
    }

    suspend fun getMedicineById(id: String): Medicine? {
        delay(1000)
        return inventoryRepository.getMedicineById(id)
    }
}
