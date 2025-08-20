package infrastructure.repository

import application.ports.PharmacistRepository
import domain.model.Pharmacist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InMemoryPharmacistRepository : PharmacistRepository {
    private val pharmacists = mutableListOf<Pharmacist>()

    override suspend fun save(pharmacist: Pharmacist)= withContext(Dispatchers.IO) {
        pharmacists.add(pharmacist)
    }

    override suspend fun findById(id: String): Pharmacist?=withContext(Dispatchers.IO) {
        return@withContext pharmacists.find { it.id == id }
    }

    override suspend fun findAll(): List<Pharmacist> = withContext(Dispatchers.IO) {
        return@withContext pharmacists.toList()
    }

    override suspend fun deleteById(id: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext pharmacists.removeIf { it.id == id }
    }
}
