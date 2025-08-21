package ui

import application.service.PrescriptionService
import domain.model.PrescriptionItem
import domain.model.PrescriptionStatus

object PrescriptionMenu {
    suspend fun show(prescriptionService: PrescriptionService) {
        while (true) {
            println("\n--- Prescription Menu ---")
            println("1. Create Prescription")
            println("2. Get Prescription by ID")
            println("3. List Prescriptions by Customer")
            println("4. List Prescriptions by Pharmacist")
            println("5. List All Prescriptions")
            println("6. Back")
            print("Choose an option: ")

            when (readLine()?.trim()) {
                "1" -> {
                    print("Enter customer ID: ")
                    val customerId = readLine()!!
                    print("Enter pharmacist ID: ")
                    val pharmacistId = readLine()!!

                    val items = mutableListOf<PrescriptionItem>()
                    while (true) {
                        print("Enter medicine ID to add it to the prescription (or press Enter to finish): ")
                        val medId = readLine()!!.trim()
                        if (medId.isEmpty()) break

                        print("Enter quantity: ")
                        val qty = readLine()!!.toInt()

                        val med = prescriptionService.getMedicineById(medId)
                        if (med == null) {
                            println("Medicine not found.")
                            continue
                        }


                        items.add(PrescriptionItem(medicine = med, quantity = qty))


                        print("Do you want to add another medicine? (y/n): ")
                        val choice = readLine()?.trim()?.lowercase()
                        if (choice != "y" && choice != "yes") {
                            break
                        }
                    }

                    val status = prescriptionService.createPrescription(customerId , pharmacistId, items)

                    when (status) {
                        is PrescriptionStatus.Approved -> println("Prescription approved and saved.")
                        is PrescriptionStatus.Rejected -> println("Rejected: ${status.reason}")
                        else -> {println("Unknown status.")}
                    }
                }
                "2" -> {
                    print("Enter prescription ID: ")
                    val id = readLine()!!
                    val prescription = prescriptionService.getPrescriptionById(id)
                    println(prescription ?: "Prescription not found.")
                }
                "3" -> {
                    print("Enter customer ID: ")
                    val id = readLine()!!
                    val prescriptions = prescriptionService.getPrescriptionsByCustomer(id)
                    if (prescriptions.isEmpty()){
                        println("No Prescription found for this customer.")
                    }
                    prescriptions.forEach { println(it) }
                }
                "4" -> {
                    print("Enter pharmacist ID: ")
                    val id = readLine()!!
                    val prescriptions = prescriptionService.getPrescriptionsByPharmacist(id)
                    if (prescriptions.isEmpty()){
                        println("No Prescription found for this customer Pharmacist.")
                    }
                    prescriptions.forEach { println(it) }
                }
                "5" -> {
                    val prescriptions = prescriptionService.getAllPrescriptions()
                    if (prescriptions.isEmpty()){
                        println("No prescription yet.")
                    }
                    prescriptions.forEach { println(it) }
                }
                "6" -> return
                else -> println("Invalid option.")
            }
        }
    }
}
