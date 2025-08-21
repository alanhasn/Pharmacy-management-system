package ui

import application.service.InventoryService
import domain.model.InventoryItem
import domain.model.Medicine
import ui.utils.Colors
import ui.utils.UiUtils

object InventoryMenu {
    suspend fun show(inventoryService: InventoryService) {
        while (true) {
            println("\n${Colors.BRIGHT_CYAN}--- Inventory Menu ---${Colors.RESET}")
            println("${Colors.YELLOW} 1.${Colors.RESET} Add Medicine (define + initial quantity)")
            println("${Colors.YELLOW} 2.${Colors.RESET} List Medicines")
            println("${Colors.YELLOW} 3.${Colors.RESET} Add Stock to existing medicine")
            println("${Colors.YELLOW} 4.${Colors.RESET} Reduce Stock (dispense)")
            println("${Colors.YELLOW} 5.${Colors.RESET} View Inventory Items (medicine + quantity)")
            println("${Colors.YELLOW} 6.${Colors.RESET} Back")
            print("${Colors.BRIGHT_GREEN} Choose an option: ${Colors.RESET}")

            when (readLine()?.trim()) {
                "1" -> addMedicine(inventoryService)
                "2" -> listMedicines(inventoryService)
                "3" -> addStock(inventoryService)
                "4" -> reduceStock(inventoryService)
                "5" -> viewInventoryItems(inventoryService)
                "6" -> return
                else -> println("${Colors.BRIGHT_RED} Invalid option.${Colors.RESET}")
            }
        }
    }

    private suspend fun addMedicine(inventoryService: InventoryService) {
        val id = UiUtils.generateIdOrRead("Enter medicine ID")
        val name = UiUtils.readNonEmpty("Enter the name: ")
        val sku = UiUtils.readNonEmpty("Enter SKU: ")
        val expiresAt = UiUtils.readInstantOrNull("Enter expiration date")
        val price = UiUtils.readDouble("Enter the price: ") ?: 0.0
        val minAge = UiUtils.readInt("Enter minimum age to use this medicine (or empty): ", allowEmpty = true)
        val quantity = UiUtils.readInt("Enter initial quantity: ") ?: 0
        val requiresSpecial = UiUtils.readBoolean("Requires special license?")

        val medicine = Medicine(
            id = id,
            name = name,
            sku = sku,
            expiresAt = expiresAt,
            price = price,
            minAge = minAge,
            quantity = quantity,
            requiresSpecialLicense = requiresSpecial
        )
        val item = InventoryItem(medicine = medicine, quantity = quantity)
        inventoryService.addMedicine(item)
        println("${Colors.BRIGHT_GREEN} Medicine added to inventory.${Colors.RESET}")
    }

    private suspend fun listMedicines(inventoryService: InventoryService) {
        val meds = inventoryService.getAllMedicines()
        println("\n${Colors.BRIGHT_CYAN}=== All Medicines ===${Colors.RESET}")
        if (meds.isEmpty()) {
            println("${Colors.BRIGHT_YELLOW} No medicines added yet.${Colors.RESET}")
            return
        }

        meds.forEachIndexed { index, it ->
            println("${Colors.BRIGHT_PURPLE}Item ${index + 1}:${Colors.RESET}")
            println("  ${Colors.CYAN}Medicine ID:${Colors.RESET} ${it.medicine.id}")
            println("  ${Colors.CYAN}Name:${Colors.RESET} ${it.medicine.name}")
            println("  ${Colors.CYAN}Quantity:${Colors.RESET} ${it.quantity}")
            println("  ${Colors.CYAN}SKU:${Colors.RESET} ${it.medicine.sku}")
            println("  ${Colors.CYAN}Price:${Colors.RESET} ${it.medicine.price}")
            println("  ${Colors.CYAN}Expires At:${Colors.RESET} ${it.medicine.expiresAt}")
            println("  ${Colors.CYAN}Min age:${Colors.RESET} ${it.medicine.minAge}")
            println("  ${Colors.CYAN}Special license:${Colors.RESET} ${it.medicine.requiresSpecialLicense}")
            println("${Colors.BRIGHT_BLACK}------------------------------${Colors.RESET}")
        }

    }

    private suspend fun addStock(inventoryService: InventoryService) {
        val id = UiUtils.readNonEmpty("Enter medicine ID to add stock: ")
        val qty = UiUtils.readInt("Enter quantity to add: ") ?: 0
        val ok = inventoryService.addStock(id, qty)
        println(if (ok) "${Colors.BRIGHT_GREEN} Stock added.${Colors.RESET}" else "${Colors.BRIGHT_RED} Medicine not found.${Colors.RESET}")
    }

    private suspend fun reduceStock(inventoryService: InventoryService) {
        val id = UiUtils.readNonEmpty("Enter medicine ID to reduce: ")
        val qty = UiUtils.readInt("Enter quantity to reduce: ") ?: 0
        val ok = inventoryService.reduceStock(id, qty)
        println(if (ok) "${Colors.BRIGHT_GREEN} Stock reduced.${Colors.RESET}" else "${Colors.BRIGHT_RED} Not enough stock or medicine not found.${Colors.RESET}")
    }

    private suspend fun viewInventoryItems(inventoryService: InventoryService) {
        val items = inventoryService.getAllInventoryItems()
        println("\n${Colors.BRIGHT_CYAN}=== Inventory Items ===${Colors.RESET}")
        if (items.isEmpty()) {
            println("${Colors.BRIGHT_YELLOW} No inventory items added yet.${Colors.RESET}")
            return
        }

        items.forEachIndexed{ index , it ->
            println("${Colors.BRIGHT_PURPLE}Inventory Item ${index + 1}:${Colors.RESET}")
            println("  ${Colors.CYAN}Medicine ID:${Colors.RESET} ${it.medicine.id}")
            println("  ${Colors.CYAN}Name:${Colors.RESET} ${it.medicine.name}")
            println("  ${Colors.CYAN}Quantity:${Colors.RESET} ${it.quantity}")
        }
    }
}
