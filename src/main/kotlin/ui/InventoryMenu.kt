package ui

import application.service.InventoryService
import domain.model.InventoryItem
import domain.model.Medicine


object InventoryMenu {
    suspend fun show(inventoryService: InventoryService) {
        while (true) {
            println("\n--- Inventory Menu ---")
            println("1. Add Medicine (define + initial quantity)")
            println("2. List Medicines")
            println("3. Add Stock to existing medicine")
            println("4. Reduce Stock (dispense)")
            println("5. View Inventory Items (medicine + quantity)")
            println("6. Back")
            print("Choose an option: ")

            when (readLine()?.trim()) {
                "1" -> {
                    val id = UiUtils.generateIdOrRead("Enter medicine ID: ")
                    val name = UiUtils.readNonEmpty("Enter the name: ")
                    val sku = UiUtils.readNonEmpty("Enter SKU: ")
                    val expiresAt = UiUtils.readInstantOrNull("Enter expiration date: ")
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
                    println("Medicine added to inventory.")
                }
                "2" -> {
                    val meds = inventoryService.getAllMedicines()
                    println("=== All Medicines ===")
                    if (meds.isEmpty()){
                        println("No medicines added yet")
                    }
                    meds.forEach { m ->
                        println("ID:${m.medicine.id}, name:${m.medicine.name}, sku:${m.medicine.sku}, price:${m.medicine.price}, qty:${m.quantity}, expires:${m.medicine.expiresAt}, minAge:${m.medicine.minAge}, special:${m.medicine.requiresSpecialLicense}")
                    }
                }
                "3" -> {
                    val id = UiUtils.readNonEmpty("Enter medicine ID to add stock: ")
                    val qty = UiUtils.readInt("Enter quantity to add: ") ?: 0
                    val ok = inventoryService.addStock(id, qty)
                    println(if (ok) "Stock added." else "Medicine not found.")
                }
                "4" -> {
                    val id = UiUtils.readNonEmpty("Enter medicine ID to reduce: ")
                    val qty = UiUtils.readInt("Enter quantity to reduce: ") ?: 0
                    val ok = inventoryService.reduceStock(id, qty)
                    println(if (ok) "Stock reduced." else "Not enough stock or medicine not found.")
                }
                "5" -> {
                    val items = inventoryService.getAllInventoryItems()
                    println("=== Inventory Items ===")
                    if (items.isEmpty()){
                        println("No inventory items added yet.")
                    }
                    items.forEach { it ->
                        println("Medicine: ${it.medicine.name} (id=${it.medicine.id}) | qty=${it.quantity}")
                    }
                }
                "6" -> return
                else -> println("Invalid option.")
            }
        }
    }
}
