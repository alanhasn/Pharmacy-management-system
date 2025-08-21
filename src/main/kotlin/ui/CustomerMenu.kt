package ui

import application.service.CustomerService
import domain.model.Customer
import ui.utils.Colors
import ui.utils.UiUtils

object CustomerMenu {

    suspend fun show(customerService: CustomerService) {
        while (true) {
            println("\n${Colors.BRIGHT_CYAN}--- Customer Menu ---${Colors.RESET}")
            println("${Colors.YELLOW}1.${Colors.RESET} Register Customer")
            println("${Colors.YELLOW}2.${Colors.RESET} List Customers")
            println("${Colors.YELLOW}3.${Colors.RESET} Get Customer by ID")
            println("${Colors.YELLOW}4.${Colors.RESET} Remove Customer")
            println("${Colors.YELLOW}5.${Colors.RESET} Back")
            print("${Colors.BRIGHT_GREEN}Choose an option: ${Colors.RESET}")

            when (readLine()?.trim()) {
                "1" -> registerCustomer(customerService)
                "2" -> listCustomers(customerService)
                "3" -> getCustomerById(customerService)
                "4" -> removeCustomer(customerService)
                "5" -> return
                else -> println("${Colors.BRIGHT_RED}Invalid option.${Colors.RESET}")
            }
        }
    }

    private suspend fun registerCustomer(customerService: CustomerService) {
        val id = UiUtils.generateIdOrRead("Enter customer ID")
        val fullName = UiUtils.readNonEmpty("Enter the full name: ")
        val birthDate = UiUtils.readLocalDate("Enter the birth date")
        val customer = Customer(id = id, fullName = fullName, birthDate = birthDate)
        val ok = customerService.registerCustomer(customer)
        println(if (ok) "${Colors.BRIGHT_GREEN}Customer registered.${Colors.RESET}"
        else "${Colors.BRIGHT_RED}Failed to register, try again.${Colors.RESET}")
    }

    private suspend fun listCustomers(customerService: CustomerService) {
        val customers = customerService.listCustomers()
        println("\n${Colors.BRIGHT_CYAN}=== All Customers ===${Colors.RESET}")
        if (customers.isEmpty()) {
            println("${Colors.BRIGHT_YELLOW}No customers yet.${Colors.RESET}")
            return
        }

        customers.forEachIndexed { index, c ->
            println("${Colors.BRIGHT_PURPLE}Customer ${index + 1}:${Colors.RESET}")
            println("  ${Colors.CYAN}ID:${Colors.RESET} ${c.id}")
            println("  ${Colors.CYAN}Full Name:${Colors.RESET} ${c.fullName}")
            println("  ${Colors.CYAN}Birth Date:${Colors.RESET} ${c.birthDate}")
            println("${Colors.BRIGHT_BLACK}------------------------------${Colors.RESET}")
        }
    }

    private suspend fun getCustomerById(customerService: CustomerService) {
        val id = UiUtils.readNonEmpty("Enter customer ID: ")
        val c = customerService.getCustomerById(id)
        if (c != null) {
            println("${Colors.BRIGHT_CYAN}Customer Info:${Colors.RESET}")
            println("  ${Colors.CYAN}ID:${Colors.RESET} ${c.id}")
            println("  ${Colors.CYAN}Full Name:${Colors.RESET} ${c.fullName}")
            println("  ${Colors.CYAN}Birth Date:${Colors.RESET} ${c.birthDate}")
        } else {
            println("${Colors.BRIGHT_RED}Customer not found.${Colors.RESET}")
        }
    }

    private suspend fun removeCustomer(customerService: CustomerService) {
        val id = UiUtils.readNonEmpty("Enter customer ID to remove: ")
        val removed = customerService.removeCustomer(id)
        println(if (removed) "${Colors.BRIGHT_GREEN}Customer removed.${Colors.RESET}"
        else "${Colors.BRIGHT_RED}Customer not found.${Colors.RESET}")
    }
}
