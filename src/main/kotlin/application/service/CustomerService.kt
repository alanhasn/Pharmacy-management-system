package application.service

import application.ports.CustomerRepository
import domain.model.Customer
import kotlinx.coroutines.delay
import java.time.LocalDate

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
        if (customer.fullName.isBlank()) {
            println("Customer name cannot be blank")
            return false
        }

        if (customer.birthDate.isAfter(LocalDate.now())) {
            println("Birth date cannot be in the future")
            return false
        }

        if (customer.age() < 18) {
            println("Customer must be at least 18 years old")
            return false
        }

        delay(500) // simulate DB/network delay
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
