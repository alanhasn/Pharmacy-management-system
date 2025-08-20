package application.ports

import domain.model.Customer

interface CustomerRepository {
    suspend fun save(customer: Customer): Boolean
    suspend fun findById(id: String): Customer?
    suspend fun findAll(): List<Customer>
    suspend fun deleteById(id: String): Boolean
}
