package application.ports

import domain.model.Pharmacist

interface PharmacistRepository {
    fun save(pharmacist: Pharmacist)
    fun findById(id: String): Pharmacist?
    fun findAll(): List<Pharmacist>
    fun deleteById(id: String): Boolean
}
