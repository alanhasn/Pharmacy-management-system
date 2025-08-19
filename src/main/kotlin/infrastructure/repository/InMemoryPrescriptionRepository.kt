package infrastructure.repository

import application.ports.PrescriptionRepository
import domain.model.Prescription

class InMemoryPrescriptionRepository: PrescriptionRepository {
    private val prescriptions= mutableListOf<Prescription>()
    
    override fun save(prescription: Prescription) {
        prescriptions.add(prescription)
    }

    override fun findById(id: String): Prescription? {
        return prescriptions.find { it.id == id }
    }

    override fun findByCustomer(customerId: String): List<Prescription> {
        return prescriptions.filter { it.customer.id == customerId }
    }

    override fun findByPharmacist(pharmacistId: String): List<Prescription> {
        return prescriptions.filter { it.pharmacist.id == pharmacistId }
    }
}