package application.ports

import domain.model.Medicine

interface InventoryRepository {

    fun save(medicine: Medicine)
    fun findById(id: String): Medicine?
    fun findAll(): List<Medicine>
    fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean
    fun reduceStock(medicineId: String, quantity: Int): Boolean
    fun addStock(medicineId: String, quantity: Int): Boolean
}
