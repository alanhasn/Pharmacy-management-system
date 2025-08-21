package ui

import application.service.CustomerService
import domain.model.Customer


object CustomerMenu {
    suspend fun show(customerService: CustomerService) {
        while (true) {
            println("\n--- Customer Menu ---")
            println("1. Register Customer")
            println("2. List Customers")
            println("3. Get Customer by ID")
            println("4. Remove Customer")
            println("6. Back")
            print("Choose an option: ")

            when (readLine()?.trim()) {
                "1" -> {
                    val id = UiUtils.generateIdOrRead("Enter customer ID")
                    val fullName = UiUtils.readNonEmpty("Enter the full name: ")
                    val birthDate = UiUtils.readLocalDate("Enter the birth date")
                    val customer = Customer(id = id, fullName = fullName, birthDate = birthDate)
                    val ok = customerService.registerCustomer(customer)
                    println(if (ok) "Customer registered." else "Failed to register, try again.")
                }
                "2" -> {
                    val customers = customerService.listCustomers()
                    println("=== All Customers ===")
                    if (customers.isEmpty()){
                        println("No customers yet.")
                    }
                    customers.forEach {
                        println("ID:${it.id}, Name:${it.fullName}, birthDate:${it.birthDate}")
                    }
                }
                "3" -> {
                    val id = UiUtils.readNonEmpty("Enter customer ID: ")
                    val c = customerService.getCustomerById(id)
                    println(c ?: "Customer not found.")
                }
                "4" -> {
                    val id = UiUtils.readNonEmpty("Enter customer ID to remove: ")
                    val removed = customerService.removeCustomer(id)
                    println(if (removed) "Customer removed." else "Customer not found.")
                }
                "6" -> return
                else -> println("Invalid option.")
            }
        }
    }
}
