package application.ports

import domain.model.Customer

interface CustomerRepository {
    fun save(customer: Customer)
    fun findById(id: String): Customer?
    fun findAll(): List<Customer>
    fun deleteById(id: String): Boolean
}
