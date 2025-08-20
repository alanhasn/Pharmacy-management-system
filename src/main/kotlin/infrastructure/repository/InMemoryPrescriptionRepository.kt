package infrastructure.repository

import application.ports.PrescriptionRepository
import domain.model.Prescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
    override suspend fun save(prescription: Prescription)= withContext(Dispatchers.IO) {
        prescriptions.add(prescription)
    }

    /**
     * Delete a prescription from the repository
     * @param prescription
     * return void
     */
    override suspend fun delete(prescription: Prescription) = withContext(Dispatchers.IO) {
        prescriptions.removeIf { it.id == prescription.id }
    }

    /**
     * Delete a prescription from the repository by its id
     * @param id
     * return void
     */
    override suspend fun deleteById(id: String) = withContext(Dispatchers.IO) {
        prescriptions.removeIf { it.id == id }
    }

    /**
     * Find a prescription by its id
     * @param id
     * @return
     */
    override suspend fun findById(id: String): Prescription? = withContext(Dispatchers.IO) {
        return@withContext prescriptions.find { it.id == id }
    }

    /**
     * Find all prescriptions for a customer
     * @param customerId
     * @return List<Prescription>
     */
    override suspend fun findByCustomer(customerId: String): List<Prescription> = withContext(Dispatchers.IO) {
        return@withContext prescriptions.filter { it.customer.id == customerId }
    }

    /**
     * Find all prescriptions for a pharmacist
     * @param pharmacistId
     * @return List<Prescription>
     */
    override suspend fun findByPharmacist(pharmacistId: String): List<Prescription> = withContext(Dispatchers.IO) {
        return@withContext prescriptions.filter { it.pharmacist.id == pharmacistId }
    }

    /**
     * Find all prescriptions
     * @return List<Prescription>
     */
    override suspend fun findAll(): List<Prescription> = withContext(Dispatchers.IO) {
        return@withContext prescriptions.toList()
    }
}
