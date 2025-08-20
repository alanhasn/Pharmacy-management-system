package application.service

import application.ports.InventoryRepository
import domain.model.InventoryItem
import domain.model.Medicine
import kotlinx.coroutines.delay
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
    suspend fun addMedicine(inventoryItem: InventoryItem) {
        if (inventoryItem.medicine.expiresAt?.isBefore(Instant.now()) == true) {
            throw IllegalArgumentException("Cannot add expired medicine to inventory")
        }
        delay(1000)
        inventoryRepository.save(inventoryItem)
    }

    /**
     * Increase stock
     * Rule: Quantity must be positive
     */
    suspend fun addStock(medicineId: String, quantity: Int): Boolean {
        require(quantity > 0) { "Quantity must be positive" }
        delay(1000)
        return inventoryRepository.addStock(medicineId, quantity)
    }

    /**
     * Reduce stock
     * Rule: Cannot reduce below zero
     */
    suspend fun reduceStock(medicineId: String, quantity: Int): Boolean {
        require(quantity > 0) { "Quantity must be positive" }
        val available = inventoryRepository.isAvailable(medicineId, quantity)
        if (!available) {
            return false
        }
        delay(1000)
        return inventoryRepository.reduceStock(medicineId, quantity)
    }

    /**
     * Check availability
     */
    suspend fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean {
        delay(1000)
        return inventoryRepository.isAvailable(medicineId, requiredQuantity)
    }

    suspend fun getMedicineById(medicineId: String): Medicine? {
        delay(1000)
        return inventoryRepository.findById(medicineId)
    }

    suspend fun getAllMedicines(): List<InventoryItem> {
        delay(1000)
        return inventoryRepository.findAll()
    }

}
