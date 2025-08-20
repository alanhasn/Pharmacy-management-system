package application.service

import application.ports.PharmacistRepository
import domain.model.Pharmacist
import kotlinx.coroutines.delay

class PharmacistService(
    private val pharmacistRepository: PharmacistRepository
) {
    /**
     * Register a new pharmacist with validation rules:
     * - Full name must not be blank
     * - License number must be unique
     */
    suspend fun registerPharmacist(pharmacist: Pharmacist): Boolean {
        require(pharmacist.fullName.isNotBlank()) { "Pharmacist name cannot be blank" }
        require(pharmacist.licenseNo.isNotBlank()) { "License number cannot be blank" }

        val existing = pharmacistRepository.findAll()
        if (existing.any { it.licenseNo == pharmacist.licenseNo }) {
            println("Pharmacist with this license number already exists")
            return false
        }
        delay(1000)
        pharmacistRepository.save(pharmacist)
        return true
    }

    suspend fun getPharmacistById(id: String): Pharmacist? {
        delay(1000)
        return pharmacistRepository.findById(id)
    }

    suspend fun listPharmacists(): List<Pharmacist> {
        delay(1000)
        return pharmacistRepository.findAll()
    }

    /**
     * Remove pharmacist only if they exist
     */
    suspend fun removePharmacist(id: String): Boolean {
        val pharmacist = pharmacistRepository.findById(id)
        if (pharmacist == null) return false
        delay(1000)
        return pharmacistRepository.deleteById(pharmacist.id)
    }
}
