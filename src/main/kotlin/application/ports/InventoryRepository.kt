package application.ports

import domain.model.InventoryItem
import domain.model.Medicine

interface InventoryRepository {
    suspend fun save(item: InventoryItem): Any
    suspend fun findByMedicineId(id: String): InventoryItem?
    suspend fun findAll(): List<InventoryItem>
    suspend fun findById(id: String): Medicine?
    suspend fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean
    suspend fun reduceStock(medicineId: String, quantity: Int): Boolean
    suspend fun addStock(medicineId: String, quantity: Int): Boolean
}
