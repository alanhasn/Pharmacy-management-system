package domain.model

import java.time.LocalDate
import java.time.Period
import java.util.UUID

/**
 * Data class for customer
 */
data class Customer(
    val id: String = UUID.randomUUID().toString(),
    val fullName: String,
    val birthDate: LocalDate
) {
    fun age(): Int = Period.between(birthDate, LocalDate.now()).years
}
