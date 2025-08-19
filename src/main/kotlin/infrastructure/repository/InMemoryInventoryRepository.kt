package infrastructure.repository

import application.ports.InventoryRepository
import domain.model.Medicine

class InMemoryInventoryRepository : InventoryRepository {
    private val medicines = mutableListOf<Medicine>()

    override fun save(medicine: Medicine) {
        medicines.add(medicine)
    }

    override fun findById(id: String): Medicine? {
        return medicines.find { it.id == id }
    }

    override fun findAll(): List<Medicine> {
        return medicines.toList()
    }

    override fun isAvailable(medicineId: String, requiredQuantity: Int): Boolean {
        val medicine = medicines.find { it.id == medicineId }
        val available = medicine != null && medicine.quantity >= requiredQuantity
        return available
    }

    override fun reduceStock(medicineId: String, quantity: Int): Boolean {
        val medicine = medicines.find { it.id == medicineId }
        return if (medicine != null) {
            if (medicine.quantity >= quantity) {
                medicine.quantity -= quantity
                true
            } else {
                false
            }
        } else {
            false
        }
    }

    override fun addStock(medicineId: String, quantity: Int): Boolean {
        val medicine = medicines.find { it.id == medicineId }
        return if (medicine != null) {
            medicine.quantity += quantity
            true
        } else {
            false
        }
    }
}
