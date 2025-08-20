package infrastructure.repository

import application.ports.PrescriptionRepository
import domain.model.Prescription

/**
 * Class that implements the PrescriptionRepository interface
 * using in-memory storage
 */
class InMemoryPrescriptionRepository : PrescriptionRepository {
    private val prescriptions = mutableListOf<Prescription>()

    /**
     * Save a prescription in the repository
     * @param prescription
     * return void
     */
    override fun save(prescription: Prescription) {
        prescriptions.add(prescription)
    }

    /**
     * Delete a prescription from the repository
     * @param prescription
     * return void
     */
    override fun delete(prescription: Prescription) {
        prescriptions.removeIf { it.id == prescription.id }
    }

    /**
     * Delete a prescription from the repository by its id
     * @param id
     * return void
     */
    override fun deleteById(id: String) {
        prescriptions.removeIf { it.id == id }
    }

    /**
     * Find a prescription by its id
     * @param id
     * @return
     */
    override fun findById(id: String): Prescription? {
        return prescriptions.find { it.id == id }
    }

    /**
     * Find all prescriptions for a customer
     * @param customerId
     * @return List<Prescription>
     */
    override fun findByCustomer(customerId: String): List<Prescription> {
        return prescriptions.filter { it.customer.id == customerId }
    }

    /**
     * Find all prescriptions for a pharmacist
     * @param pharmacistId
     * @return List<Prescription>
     */
    override fun findByPharmacist(pharmacistId: String): List<Prescription> {
        return prescriptions.filter { it.pharmacist.id == pharmacistId }
    }

    /**
     * Find all prescriptions
     * @return List<Prescription>
     */
    override fun findAll(): List<Prescription> {
        return prescriptions.toList()
    }
}
