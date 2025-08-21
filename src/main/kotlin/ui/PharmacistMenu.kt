package ui

import application.service.PharmacistService
import domain.model.Pharmacist

object PharmacistMenu {
    suspend fun show(pharmacistService: PharmacistService) {
        while (true) {
            println("\n--- Pharmacist Menu ---")
            println("1. Register Pharmacist")
            println("2. List Pharmacists")
            println("3. Get Pharmacist by ID")
            println("4. Remove Pharmacist")
            println("6. Back")
            print("Choose an option: ")

            when (readLine()?.trim()) {
                "1" -> {
                    val id = UiUtils.generateIdOrRead("Enter pharmacist ID")
                    val name = UiUtils.readNonEmpty("Enter full name: ")
                    val licenseNo = UiUtils.readNonEmpty("Enter license number: ")
                    val hasSpecial = UiUtils.readBoolean("Has special license?")
                    val pharmacist = Pharmacist(id = id, fullName = name, licenseNo = licenseNo, hasSpecialLicense = hasSpecial)
                    val ok = pharmacistService.registerPharmacist(pharmacist)
                    println(if (ok) "Pharmacist registered." else "Failed to register.")
                }
                "2" -> {
                    val list = pharmacistService.listPharmacists()
                    println("=== Pharmacists ===")
                    if (list.isEmpty()){
                        println("No Pharmacists added yet.")
                    }
                    list.forEach { p ->
                        println("ID:${p.id}, Name:${p.fullName}, License:${p.licenseNo}, special:${p.hasSpecialLicense}")
                    }
                }
                "3" -> {
                    val id = UiUtils.readNonEmpty("Enter pharmacist ID: ")
                    val p = pharmacistService.getPharmacistById(id)
                    println(p ?: "Pharmacist not found.")
                }
                "4" -> {
                    val id = UiUtils.readNonEmpty("Enter pharmacist ID to remove: ")
                    val removed = pharmacistService.removePharmacist(id)
                    println(if (removed) "Pharmacist removed." else "Pharmacist not found.")
                }
                "6" -> return
                else -> println("Invalid option.")
            }
        }
    }
}
