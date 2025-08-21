package composition

import application.service.CustomerService
import application.service.InventoryService
import application.service.PharmacistService
import application.service.PrescriptionService
import infrastructure.repository.InMemoryCustomerRepository
import infrastructure.repository.InMemoryInventoryRepository
import infrastructure.repository.InMemoryPharmacistRepository
import infrastructure.repository.InMemoryPrescriptionRepository

/**
 * Build functions that create in-memory repositories and the services.
 */

fun buildInventoryService(): InventoryService {
    val repo = InMemoryInventoryRepository()
    return InventoryService(repo)
}

fun buildCustomerService(): CustomerService {
    val repo = InMemoryCustomerRepository()
    return CustomerService(repo)
}

fun buildPharmacistService(): PharmacistService {
    val repo = InMemoryPharmacistRepository()
    return PharmacistService(repo)
}

fun buildPrescriptionService(
    inventoryService: InventoryService,
    customerService: CustomerService,
    pharmacistService: PharmacistService
): PrescriptionService {
    val presRepo = InMemoryPrescriptionRepository()
    return PrescriptionService(
        prescriptionRepository = presRepo,
        inventoryRepository = inventoryService,
        customerService = customerService,
        pharmacistService = pharmacistService
    )
}
