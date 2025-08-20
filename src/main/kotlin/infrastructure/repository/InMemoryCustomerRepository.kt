package infrastructure.repository

import application.ports.CustomerRepository
import domain.model.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InMemoryCustomerRepository : CustomerRepository {
    private val customers = mutableListOf<Customer>()

    override suspend fun save(customer: Customer) = withContext(Dispatchers.IO) {
        customers.add(customer)
    }

    override suspend fun findById(id: String): Customer? = withContext(Dispatchers.IO) {
        return@withContext customers.find { it.id == id }
    }

    override suspend fun findAll(): List<Customer> = withContext(Dispatchers.IO) {
        return@withContext customers.toList()
    }

    override suspend fun deleteById(id: String): Boolean = withContext(Dispatchers.IO) {
        return@withContext customers.removeIf { it.id == id }
    }
}
