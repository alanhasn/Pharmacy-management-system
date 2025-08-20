package application.ports

import domain.model.Prescription

interface PrescriptionRepository {
    suspend fun save(prescription: Prescription): Boolean
    suspend fun delete(prescription: Prescription): Boolean
    suspend fun deleteById(id: String): Boolean
    suspend fun findById(id: String): Prescription?
    suspend fun findByCustomer(customerId: String): List<Prescription>
    suspend fun findByPharmacist(pharmacistId: String): List<Prescription>
    suspend fun findAll(): List<Prescription>
}
