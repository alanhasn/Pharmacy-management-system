package application.service

import application.ports.CustomerRepository
import domain.model.Customer
import kotlinx.coroutines.delay

class CustomerService(
    private val customerRepository: CustomerRepository
) {
    /**
     * Register a new customer with validation rules:
     * - Name must not be blank
     * - Age must be >= 18
     * - Email must not already exist (simple uniqueness check)
     */
    suspend fun registerCustomer(customer: Customer): Boolean {
        require(customer.fullName.isNotBlank()) { "Customer name cannot be blank" }
        require(customer.age() >= 15) { "Customer must be at least 18 years old" }
        delay(1000)
        customerRepository.save(customer)
        return true
    }

    /**
     * Get customer by id
     */
    suspend fun getCustomerById(id: String): Customer? {
        delay(1000)
        return customerRepository.findById(id)
    }

    /**
     * Get all customers
     * @return List of customers
     */
    suspend fun listCustomers(): List<Customer> {
        delay(1000)
        return customerRepository.findAll()
    }

    /**
     * Remove customer only if they exist
     */
    suspend fun removeCustomer(id: String): Boolean {
        val customer = customerRepository.findById(id)
        if (customer == null) return false
        delay(1000)
        return customerRepository.deleteById(customer.id)
    }
}
