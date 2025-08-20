package infrastructure.repository

import application.ports.PharmacistRepository
import domain.model.Pharmacist

class InMemoryPharmacistRepository : PharmacistRepository {
    private val pharmacists = mutableListOf<Pharmacist>()

    override fun save(pharmacist: Pharmacist) {
        pharmacists.add(pharmacist)
    }

    override fun findById(id: String): Pharmacist? {
        return pharmacists.find { it.id == id }
    }

    override fun findAll(): List<Pharmacist> {
        return pharmacists.toList()
    }

    override fun deleteById(id: String): Boolean {
        return pharmacists.removeIf { it.id == id }
    }
}
