package ui

import application.service.PharmacistService
import domain.model.Pharmacist
import ui.utils.Colors
import ui.utils.UiUtils

object PharmacistMenu {

    suspend fun show(pharmacistService: PharmacistService) {
        while (true) {
            println("\n${Colors.BRIGHT_CYAN}--- Pharmacist Menu ---${Colors.RESET}")
            println("${Colors.YELLOW}1.${Colors.RESET} Register Pharmacist")
            println("${Colors.YELLOW}2.${Colors.RESET} List Pharmacists")
            println("${Colors.YELLOW}3.${Colors.RESET} Get Pharmacist by ID")
            println("${Colors.YELLOW}4.${Colors.RESET} Remove Pharmacist")
            println("${Colors.YELLOW}5.${Colors.RESET} Back")
            print("${Colors.BRIGHT_GREEN}Choose an option: ${Colors.RESET}")

            when (readLine()?.trim()) {
                "1" -> registerPharmacist(pharmacistService)
                "2" -> listPharmacists(pharmacistService)
                "3" -> getPharmacistById(pharmacistService)
                "4" -> removePharmacist(pharmacistService)
                "5" -> return
                else -> println("${Colors.BRIGHT_RED}Invalid option.${Colors.RESET}")
            }
        }
    }

    private suspend fun registerPharmacist(pharmacistService: PharmacistService) {
        val id = UiUtils.generateIdOrRead("Enter pharmacist ID")
        val name = UiUtils.readNonEmpty("Enter full name: ")
        val licenseNo = UiUtils.readNonEmpty("Enter license number: ")
        val hasSpecial = UiUtils.readBoolean("Has special license?")
        val pharmacist = Pharmacist(
            id = id,
            fullName = name,
            licenseNo = licenseNo,
            hasSpecialLicense = hasSpecial
        )
        val ok = pharmacistService.registerPharmacist(pharmacist)
        println(if (ok) "${Colors.BRIGHT_GREEN}Pharmacist registered.${Colors.RESET}"
        else "${Colors.BRIGHT_RED}Failed to register.${Colors.RESET}")
    }

    private suspend fun listPharmacists(pharmacistService: PharmacistService) {
        val list = pharmacistService.listPharmacists()
        println("\n${Colors.BRIGHT_CYAN}=== Pharmacists ===${Colors.RESET}")
        if (list.isEmpty()) {
            println("${Colors.BRIGHT_YELLOW}No pharmacists added yet.${Colors.RESET}")
            return
        }

        list.forEachIndexed { index, p ->
            println("${Colors.BRIGHT_PURPLE}Pharmacist ${index + 1}:${Colors.RESET}")
            println("  ${Colors.CYAN}ID:${Colors.RESET} ${p.id}")
            println("  ${Colors.CYAN}Full Name:${Colors.RESET} ${p.fullName}")
            println("  ${Colors.CYAN}License No:${Colors.RESET} ${p.licenseNo}")
            println("  ${Colors.CYAN}Has Special License:${Colors.RESET} ${if (p.hasSpecialLicense) "Yes" else "No"}")
            println("${Colors.BRIGHT_BLACK}------------------------------${Colors.RESET}")
        }
    }

    private suspend fun getPharmacistById(pharmacistService: PharmacistService) {
        val id = UiUtils.readNonEmpty("Enter pharmacist ID: ")
        val p = pharmacistService.getPharmacistById(id)
        if (p != null) {
            println("${Colors.BRIGHT_CYAN}Pharmacist Info:${Colors.RESET}")
            println("  ${Colors.CYAN}ID:${Colors.RESET} ${p.id}")
            println("  ${Colors.CYAN}Full Name:${Colors.RESET} ${p.fullName}")
            println("  ${Colors.CYAN}License No:${Colors.RESET} ${p.licenseNo}")
            println("  ${Colors.CYAN}Has Special License:${Colors.RESET} ${if (p.hasSpecialLicense) "Yes" else "No"}")
        } else {
            println("${Colors.BRIGHT_RED}Pharmacist not found.${Colors.RESET}")
        }
    }

    private suspend fun removePharmacist(pharmacistService: PharmacistService) {
        val id = UiUtils.readNonEmpty("Enter pharmacist ID to remove: ")
        val removed = pharmacistService.removePharmacist(id)
        println(if (removed) "${Colors.BRIGHT_GREEN}Pharmacist removed.${Colors.RESET}"
        else "${Colors.BRIGHT_RED}Pharmacist not found.${Colors.RESET}")
    }
}
