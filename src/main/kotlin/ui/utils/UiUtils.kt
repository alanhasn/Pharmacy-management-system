package ui.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

object UiUtils {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun readLineTrim(prompt: String): String {
        print(prompt)
        return readLine()?.trim().orEmpty()
    }

    fun readNonEmpty(prompt: String): String {
        while (true) {
            val s = readLineTrim(prompt)
            if (s.isNotEmpty()) return s
            println("Value cannot be empty. Please try again.")
        }
    }

    fun readInt(prompt: String, allowEmpty: Boolean = false): Int? {
        while (true) {
            val s = readLineTrim(prompt)
            if (s.isEmpty() && allowEmpty) return null
            try {
                return s.toInt()
            } catch (e: Exception) {
                println("Enter a valid integer.")
            }
        }
    }

    fun readDouble(prompt: String, allowEmpty: Boolean = false): Double? {
        while (true) {
            val s = readLineTrim(prompt)
            if (s.isEmpty() && allowEmpty) return null
            try {
                return s.toDouble()
            } catch (e: Exception) {
                println("Enter a valid number (e.g. 12.5).")
            }
        }
    }

    fun readBoolean(prompt: String): Boolean {
        while (true) {
            val s = readLineTrim("$prompt (y/n): ").lowercase(Locale.getDefault())
            if (s in listOf("y", "yes")) return true
            if (s in listOf("n", "no")) return false
            println("Enter 'y' or 'n'.")
        }
    }

    fun readLocalDate(prompt: String): LocalDate {
        while (true) {
            val s = readLineTrim("$prompt (YYYY-MM-DD): ")
            try {
                return LocalDate.parse(s, dateFormatter)
            } catch (e: Exception) {
                println("Invalid date format. Use YYYY-MM-DD.")
            }
        }
    }

    fun readInstantOrNull(prompt: String): Instant? {
        while (true) {
            val s = readLineTrim("$prompt (YYYY-MM-DD or empty): ")
            if (s.isEmpty()) return null
            try {
                val ld = LocalDate.parse(s, dateFormatter)
                return ld.atStartOfDay(ZoneOffset.UTC).toInstant()
            } catch (e: Exception) {
                println("Invalid date format. Use YYYY-MM-DD or empty.")
            }
        }
    }

    fun generateIdOrRead(prompt: String): String {
        val s = readLineTrim("$prompt (enter to auto-generate): ")
        return s.ifEmpty { UUID.randomUUID().toString() }
    }
}