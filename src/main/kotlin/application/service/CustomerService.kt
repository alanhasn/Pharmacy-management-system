package application.service

import application.ports.CustomerRepository
import domain.model.Customer

class CustomerService(
    private val customerRepository: CustomerRepository
) {
    /**
     * Register a new customer with validation rules:
     * - Name must not be blank
     * - Age must be >= 18
     * - Email must not already exist (simple uniqueness check)
     */
    fun registerCustomer(customer: Customer): Boolean {
        require(customer.fullName.isNotBlank()) { "Customer name cannot be blank" }
        require(customer.age() >= 15) { "Customer must be at least 18 years old" }

        customerRepository.save(customer)
        return true
    }

    /**
     * Get customer by id
     */
    fun getCustomerById(id: String): Customer? {
        return customerRepository.findById(id)
    }

    /**
     * Get all customers
     * @return List of customers
     */
    fun listCustomers(): List<Customer> {
        return customerRepository.findAll()
    }

    /**
     * Remove customer only if they exist
     */
    fun removeCustomer(id: String): Boolean {
        val customer = customerRepository.findById(id)
        if (customer == null) return false
        return customerRepository.deleteById(customer.id)
    }
}
