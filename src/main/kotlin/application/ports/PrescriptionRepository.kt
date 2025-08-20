package application.ports

import domain.model.Prescription

interface PrescriptionRepository {

    fun save(prescription: Prescription)
    fun delete(prescription: Prescription)
    fun deleteById(id: String)
    fun findById(id: String): Prescription?
    fun findByCustomer(customerId: String): List<Prescription>
    fun findByPharmacist(pharmacistId: String): List<Prescription>
    fun findAll(): List<Prescription>
}
