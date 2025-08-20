package infrastructure.repository

import application.ports.CustomerRepository
import domain.model.Customer

class InMemoryCustomerRepository : CustomerRepository {
    private val customers = mutableListOf<Customer>()

    override fun save(customer: Customer) {
        customers.add(customer)
    }

    override fun findById(id: String): Customer? {
        return customers.find { it.id == id }
    }

    override fun findAll(): List<Customer> {
        return customers.toList()
    }

    override fun deleteById(id: String): Boolean {
        return customers.removeIf { it.id == id }
    }
}
