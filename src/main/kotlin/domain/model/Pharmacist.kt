package domain.model

import java.util.UUID

data class Pharmacist(
    val id: String = UUID.randomUUID().toString(),
    val fullName: String,
    val licenseNo: String, // Official license number from the Ministry of Health
    val hasSpecialLicense: Boolean = false
)

