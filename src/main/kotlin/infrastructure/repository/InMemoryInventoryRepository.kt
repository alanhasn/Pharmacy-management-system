package infrastructure.repository

import application.ports.InventoryRepository
import domain.model.InventoryItem
import domain.model.Medicine

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
    override fun save(item: InventoryItem) {
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
    override fun findByMedicineId(id: String): InventoryItem? {
        return inventory.find { it.medicine.id == id }
    }

    /**
     * Find all inventory items
     * @param id
     * @return
     */
    override fun findAll(): List<InventoryItem> {
        return inventory.toList()
    }

    override fun findById(id: String): Medicine? {
        return inventory.find { it.medicine.id == id }?.medicine
    }

    /**
     * Check if a medicine is available in the inventory
     * @param medicineId
     * @param requiredQuantity
     */
    override fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean {
        val item = findByMedicineId(medicineId)
        return item != null && item.quantity >= requiredQuantity
    }

    /**
     * Reduce the quantity of a medicine in the inventory
     * @param medicineId
     * @param quantity
     * @return Boolean
     */
    override fun reduceStock(medicineId: String, quantity: Int): Boolean {
        val item = findByMedicineId(medicineId) // get the inventory item
        return if (item != null && item.quantity >= quantity) { // check if the quantity is available
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
    override fun addStock(medicineId: String, quantity: Int): Boolean {
        val item = findByMedicineId(medicineId)
        return if (item != null) {
            item.quantity += quantity
            true
        } else {
            false
        }
    }
}
