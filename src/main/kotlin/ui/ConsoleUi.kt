package ui

import composition.buildCustomerService
import composition.buildInventoryService
import composition.buildPharmacistService
import composition.buildPrescriptionService
import kotlinx.coroutines.runBlocking

object ConsoleUi {

    fun start()= runBlocking {
        // build services and inject dependencies
        val inventoryService = buildInventoryService()
        val customerService = buildCustomerService()
        val pharmacistService = buildPharmacistService()
        val prescriptionService = buildPrescriptionService(inventoryService, customerService, pharmacistService)

        while (true) {
            println("\n=== Pharmacy Management System ===")
            println("1. Manage Inventory")
            println("2. Manage Customers")
            println("3. Manage Pharmacists")
            println("4. Manage Prescriptions")
            println("6. Exit")
            print("Choose an option: ")

            when (readLine()?.trim()) {
                "1" -> InventoryMenu.show(inventoryService)
                "2" -> CustomerMenu.show(customerService)
                "3" -> PharmacistMenu.show(pharmacistService)
                "4" -> PrescriptionMenu.show(prescriptionService)
                "6" -> {
                    println("Goodbye!")
                    return@runBlocking
                }
                else -> println("Invalid option, please try again.")
            }
        }

    }
}