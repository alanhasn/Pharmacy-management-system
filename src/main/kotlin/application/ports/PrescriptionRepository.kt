package application.ports

import domain.model.Prescription

interface PrescriptionRepository {

    fun save(prescription: Prescription)
    fun findById(id: String): Prescription?
    fun findByCustomer(customerId: String): List<Prescription>
    fun findByPharmacist(pharmacistId: String): List<Prescription>
}
