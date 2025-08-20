package application.ports

import domain.model.InventoryItem

interface InventoryRepository {
    fun save(item: InventoryItem)
    fun findByMedicineId(id: String): InventoryItem?
    fun findAll(): List<InventoryItem>
    fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean
    fun reduceStock(medicineId: String, quantity: Int): Boolean
    fun addStock(medicineId: String, quantity: Int): Boolean
}
