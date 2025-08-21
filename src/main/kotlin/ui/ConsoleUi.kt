package ui

import composition.buildCustomerService
import composition.buildInventoryService
import composition.buildPharmacistService
import composition.buildPrescriptionService
import kotlinx.coroutines.runBlocking
import ui.utils.Colors

object ConsoleUi {

    fun start() = runBlocking {
        // build services and inject dependencies
        val inventoryService = buildInventoryService()
        val customerService = buildCustomerService()
        val pharmacistService = buildPharmacistService()
        val prescriptionService = buildPrescriptionService(inventoryService, customerService, pharmacistService)

        while (true) {
            println("\n${Colors.BRIGHT_BLUE}=== Pharmacy Management System ===${Colors.RESET}")
            println("${Colors.BRIGHT_PURPLE}1.${Colors.RESET} Manage Inventory")
            println("${Colors.BRIGHT_PURPLE}2.${Colors.RESET} Manage Customers")
            println("${Colors.BRIGHT_PURPLE}3.${Colors.RESET} Manage Pharmacists")
            println("${Colors.BRIGHT_PURPLE}4.${Colors.RESET} Manage Prescriptions")
            println("${Colors.BRIGHT_RED}5.${Colors.RESET} Exit")
            print("${Colors.BRIGHT_GREEN}Choose an option: ${Colors.RESET}")

            when (readLine()?.trim()) {
                "1" -> InventoryMenu.show(inventoryService)
                "2" -> CustomerMenu.show(customerService)
                "3" -> PharmacistMenu.show(pharmacistService)
                "4" -> PrescriptionMenu.show(prescriptionService)
                "5" -> {
                    println("${Colors.BRIGHT_GREEN}Goodbye!${Colors.RESET}")
                    return@runBlocking
                }
                else -> println("${Colors.BRIGHT_RED}Invalid option, please try again.${Colors.RESET}")
            }
        }
    }
}
