package domain.model

// sealed class to represent prescription processing states
sealed class PrescriptionStatus {
    object Pending : PrescriptionStatus()        // request is waiting
    object Approved : PrescriptionStatus()       // approved & saved
    data class Rejected(val reason: String) : PrescriptionStatus() // rejected with reason
}