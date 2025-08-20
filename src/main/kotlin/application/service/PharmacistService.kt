package application.service

import application.ports.PharmacistRepository
import domain.model.Pharmacist

class PharmacistService(
    private val pharmacistRepository: PharmacistRepository
) {
    /**
     * Register a new pharmacist with validation rules:
     * - Full name must not be blank
     * - License number must be unique
     */
    fun registerPharmacist(pharmacist: Pharmacist): Boolean {
        require(pharmacist.fullName.isNotBlank()) { "Pharmacist name cannot be blank" }
        require(pharmacist.licenseNo.isNotBlank()) { "License number cannot be blank" }

        val existing = pharmacistRepository.findAll()
        if (existing.any { it.licenseNo == pharmacist.licenseNo }) {
            println("Pharmacist with this license number already exists")
            return false
        }

        pharmacistRepository.save(pharmacist)
        return true
    }

    fun getPharmacistById(id: String): Pharmacist? {
        return pharmacistRepository.findById(id)
    }

    fun listPharmacists(): List<Pharmacist> {
        return pharmacistRepository.findAll()
    }

    /**
     * Remove pharmacist only if they exist
     */
    fun removePharmacist(id: String): Boolean {
        val pharmacist = pharmacistRepository.findById(id)
        if (pharmacist == null) return false
        return pharmacistRepository.deleteById(pharmacist.id)
    }
}
