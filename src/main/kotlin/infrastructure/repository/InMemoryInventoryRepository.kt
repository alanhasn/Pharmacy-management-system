package infrastructure.repository

import application.ports.InventoryRepository
import domain.model.InventoryItem
import domain.model.Medicine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *  Class that implements the InventoryRepository interface
 */
class InMemoryInventoryRepository : InventoryRepository {
    private val inventory = mutableListOf<InventoryItem>()

    /**
     * Save an inventory item
     * @param item
     * return void
     */
    override suspend fun save(item: InventoryItem)= withContext(Dispatchers.IO) {
        val existing = inventory.find { it.medicine.id == item.medicine.id }
        if (existing != null) {
            existing.quantity = item.quantity
        } else {
            inventory.add(item)
        }
    }

    /**
     * Find an inventory item by its medicine id
     * @param id
     * @return
     */
    override suspend fun findByMedicineId(id: String): InventoryItem?=withContext(Dispatchers.IO) {
        return@withContext inventory.find { it.medicine.id == id }
    }

    /**
     * Find all inventory items
     * @param id
     * @return
     */
    override suspend fun findAll(): List<InventoryItem> = withContext(Dispatchers.IO) {
        return@withContext inventory.toList()
    }

    override suspend fun findById(id: String): Medicine? = withContext(Dispatchers.IO) {
        return@withContext inventory.find { it.medicine.id == id }?.medicine
    }

    /**
     * Check if a medicine is available in the inventory
     * @param medicineId
     * @param requiredQuantity
     */
    override suspend fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean = withContext(Dispatchers.IO) {
        val item = findByMedicineId(medicineId)
        return@withContext item != null && item.quantity >= requiredQuantity
    }

    /**
     * Reduce the quantity of a medicine in the inventory
     * @param medicineId
     * @param quantity
     * @return Boolean
     */
    override suspend fun reduceStock(medicineId: String, quantity: Int): Boolean =withContext(Dispatchers.IO) {
        val item = findByMedicineId(medicineId) // get the inventory item
        return@withContext if (item != null && item.quantity >= quantity) { // check if the quantity is available
            item.quantity -= quantity
            true
        } else {
            false
        }
    }

    /**
     * Add the quantity of a medicine in the inventory
     * @param medicineId
     * @param quantity
     * @return Boolean
     */
    override suspend fun addStock(medicineId: String, quantity: Int): Boolean = withContext(Dispatchers.IO) {
        val item = findByMedicineId(medicineId)
        return@withContext if (item != null) {
            item.quantity += quantity
            true
        } else {
            false
        }
    }
}
