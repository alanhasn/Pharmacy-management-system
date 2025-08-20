package application.service

import application.ports.InventoryRepository
import domain.model.InventoryItem
import domain.model.Medicine
import java.time.Instant

/**
 * Class that implements the InventoryService interface
 * using the InventoryRepository
 */
class InventoryService(
    private val inventoryRepository: InventoryRepository
) {

    /**
     * Add a new medicine to the inventory
     * Business Rule: Medicine cannot be expired when added
     */
    fun addMedicine(inventoryItem: InventoryItem) {
        if (inventoryItem.medicine.expiresAt?.isBefore(Instant.now()) == true) {
            throw IllegalArgumentException("Cannot add expired medicine to inventory")
        }
        inventoryRepository.save(inventoryItem)
    }

    /**
     * Increase stock
     * Rule: Quantity must be positive
     */
    fun addStock(medicineId: String, quantity: Int): Boolean {
        require(quantity > 0) { "Quantity must be positive" }
        return inventoryRepository.addStock(medicineId, quantity)
    }

    /**
     * Reduce stock
     * Rule: Cannot reduce below zero
     */
    fun reduceStock(medicineId: String, quantity: Int): Boolean {
        require(quantity > 0) { "Quantity must be positive" }
        val available = inventoryRepository.isAvailable(medicineId, quantity)
        if (!available) {
            return false
        }
        return inventoryRepository.reduceStock(medicineId, quantity)
    }

    /**
     * Check availability
     */
    fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean {
        return inventoryRepository.isAvailable(medicineId, requiredQuantity)
    }

    fun getMedicineById(medicineId: String): Medicine? =
        inventoryRepository.findById(medicineId)

    fun getAllMedicines(): List<InventoryItem> =
        inventoryRepository.findAll()
}
