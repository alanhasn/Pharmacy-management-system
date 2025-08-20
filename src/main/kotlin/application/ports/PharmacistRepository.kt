package application.ports

import domain.model.Pharmacist

interface PharmacistRepository {
    suspend fun save(pharmacist: Pharmacist): Boolean
    suspend fun findById(id: String): Pharmacist?
    suspend fun findAll(): List<Pharmacist>
    suspend fun deleteById(id: String): Boolean
}
