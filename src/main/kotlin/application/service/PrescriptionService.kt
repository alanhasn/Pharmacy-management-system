package application.service

import application.ports.InventoryRepository
import application.ports.PrescriptionRepository
import domain.model.Prescription
import domain.model.PrescriptionItem
import domain.model.PrescriptionStatus

class PrescriptionService(
    private val prescriptionRepository: PrescriptionRepository,
    private val inventoryRepository: InventoryRepository,
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
    fun createPrescription(
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
                return PrescriptionStatus.Rejected(
                    "Failed to reduce stock for ${item.medicine.name}"
                )
            }
        }

        // 3. save prescription
        prescriptionRepository.save(prescription)
        return PrescriptionStatus.Approved
    }

    /**
     * Retrieve prescription by ID
     */
    fun getPrescriptionById(id: String): Prescription? {
        return prescriptionRepository.findById(id)
    }

    /**
     * Get all prescriptions for a given customer
     */
    fun getPrescriptionsByCustomer(customerId: String): List<Prescription> {
        return prescriptionRepository.findByCustomer(customerId)
    }

    /**
     * Get all prescriptions for a given pharmacist
     */
    fun getPrescriptionsByPharmacist(pharmacistId: String): List<Prescription> {
        return prescriptionRepository.findByPharmacist(pharmacistId)
    }

    /**
     * Get all prescriptions
     */
    fun getAllPrescriptions(): List<Prescription> {
        return prescriptionRepository.findAll()
    }
}
