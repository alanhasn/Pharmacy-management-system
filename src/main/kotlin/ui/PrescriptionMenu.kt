package ui

import application.service.PrescriptionService
import domain.model.PrescriptionItem
import domain.model.PrescriptionStatus
import ui.utils.Colors
import ui.utils.UiUtils

object PrescriptionMenu {

    suspend fun show(prescriptionService: PrescriptionService) {
        while (true) {
            println("\n${Colors.BRIGHT_CYAN}--- Prescription Menu ---${Colors.RESET}")
            println("${Colors.YELLOW}1.${Colors.RESET} Create Prescription")
            println("${Colors.YELLOW}2.${Colors.RESET} Get Prescription by ID")
            println("${Colors.YELLOW}3.${Colors.RESET} List Prescriptions by Customer")
            println("${Colors.YELLOW}4.${Colors.RESET} List Prescriptions by Pharmacist")
            println("${Colors.YELLOW}5.${Colors.RESET} List All Prescriptions")
            println("${Colors.YELLOW}6.${Colors.RESET} Back")
            print("${Colors.BRIGHT_GREEN}Choose an option: ${Colors.RESET}")

            when (readLine()?.trim()) {
                "1" -> createPrescription(prescriptionService)
                "2" -> getPrescriptionById(prescriptionService)
                "3" -> listPrescriptionsByCustomer(prescriptionService)
                "4" -> listPrescriptionsByPharmacist(prescriptionService)
                "5" -> listAllPrescriptions(prescriptionService)
                "6" -> return
                else -> println("${Colors.BRIGHT_RED}Invalid option.${Colors.RESET}")
            }
        }
    }

    private suspend fun createPrescription(prescriptionService: PrescriptionService) {
        val customerId = UiUtils.generateIdOrRead("Enter customer ID: ")
        val pharmacistId = UiUtils.readNonEmpty("Enter pharmacist ID: ")

        val items = mutableListOf<PrescriptionItem>()
        while (true) {
            val medId = UiUtils.readLineTrim("Enter medicine ID to add (or press Enter to finish): ")
            if (medId.isEmpty()) break

            val qty = UiUtils.readInt("Enter quantity: ") ?: continue
            val med = prescriptionService.getMedicineById(medId)
            if (med == null) {
                println("${Colors.BRIGHT_RED}Medicine not found.${Colors.RESET}")
                continue
            }

            items.add(PrescriptionItem(medicine = med, quantity = qty))

            val choice = UiUtils.readBoolean("Do you want to add another medicine?")
            if (!choice) break
        }

        val status = prescriptionService.createPrescription(customerId, pharmacistId, items)
        when (status) {
            is PrescriptionStatus.Approved -> println("${Colors.BRIGHT_GREEN}Prescription approved and saved.${Colors.RESET}")
            is PrescriptionStatus.Rejected -> println("${Colors.BRIGHT_RED}Rejected: ${status.reason}${Colors.RESET}")
            else -> println("${Colors.BRIGHT_YELLOW}Unknown status.${Colors.RESET}")
        }
    }

    private suspend fun getPrescriptionById(prescriptionService: PrescriptionService) {
        val id = UiUtils.readNonEmpty("Enter prescription ID: ")
        val prescription = prescriptionService.getPrescriptionById(id)
        if (prescription != null) {
            printPrescription(prescription)
        } else {
            println("${Colors.BRIGHT_RED}Prescription not found.${Colors.RESET}")
        }
    }

    private suspend fun listPrescriptionsByCustomer(prescriptionService: PrescriptionService) {
        val customerId = UiUtils.readNonEmpty("Enter customer ID: ")
        val prescriptions = prescriptionService.getPrescriptionsByCustomer(customerId)
        if (prescriptions.isEmpty()) {
            println("${Colors.BRIGHT_YELLOW}No prescriptions found for this customer.${Colors.RESET}")
            return
        }
        prescriptions.forEachIndexed { index, p ->
            println("${Colors.BRIGHT_PURPLE}Prescription ${index + 1}:${Colors.RESET}")
            printPrescription(p)
        }
    }

    private suspend fun listPrescriptionsByPharmacist(prescriptionService: PrescriptionService) {
        val pharmacistId = UiUtils.readNonEmpty("Enter pharmacist ID: ")
        val prescriptions = prescriptionService.getPrescriptionsByPharmacist(pharmacistId)
        if (prescriptions.isEmpty()) {
            println("${Colors.BRIGHT_YELLOW}No prescriptions found for this pharmacist.${Colors.RESET}")
            return
        }
        prescriptions.forEachIndexed { index, p ->
            println("${Colors.BRIGHT_PURPLE}Prescription ${index + 1}:${Colors.RESET}")
            printPrescription(p)
        }
    }

    private suspend fun listAllPrescriptions(prescriptionService: PrescriptionService) {
        val prescriptions = prescriptionService.getAllPrescriptions()
        if (prescriptions.isEmpty()) {
            println("${Colors.BRIGHT_YELLOW}No prescriptions yet.${Colors.RESET}")
            return
        }
        prescriptions.forEachIndexed { index, p ->
            println("${Colors.BRIGHT_PURPLE}Prescription ${index + 1}:${Colors.RESET}")
            printPrescription(p)
        }
    }

    private fun printPrescription(prescription: domain.model.Prescription) {
        println("  ${Colors.CYAN}Prescription ID:${Colors.RESET} ${prescription.id}")
        println("  ${Colors.CYAN}Customer ID:${Colors.RESET} ${prescription.customer.id}")
        println("  ${Colors.CYAN}Pharmacist ID:${Colors.RESET} ${prescription.pharmacist.id}")
        println("  ${Colors.CYAN}Items:${Colors.RESET}")
        prescription.items.forEach { item ->
            println("    - ${Colors.BRIGHT_BLUE}${item.medicine.name}${Colors.RESET} | Qty: ${item.quantity} | Price: ${item.medicine.price}")
        }
        println("${Colors.BRIGHT_BLACK}------------------------------${Colors.RESET}")
    }
}
