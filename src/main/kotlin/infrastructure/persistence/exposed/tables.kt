//package infrastructure.persistence.exposed
//
//import kotlinx.datetime.LocalDate
//import kotlinx.datetime.LocalDateTime
//import org.jetbrains.exposed.sql.Table
//import org.jetbrains.exposed.sql.kotlin.datetime.date
//import org.jetbrains.exposed.sql.kotlin.datetime.dateParam
//
//object MedicinesTable : Table("medicines") {
//    val id = varchar("id", 64).uniqueIndex()
//    val name = varchar("name", 255)
//    val sku = varchar("sku", 100).uniqueIndex()
//    val expiresAtMillis = long("expires_at_millis").nullable()
//    val price = double("price")
//    override val primaryKey = PrimaryKey(id)
//}
//
//object InventoryTable : Table("inventory") {
//    val medicineId = reference("medicine_id", MedicinesTable.id)
//    val quantity = integer("quantity")
//    override val primaryKey = PrimaryKey(medicineId)
//}
//
//object PharmacistsTable : Table("pharmacists") {
//    val id = varchar("id", 64).uniqueIndex()
//    val fullName = varchar("full_name", 255)
//    val licenseNo = varchar("license_no", 100).uniqueIndex()
//    override val primaryKey = PrimaryKey(id)
//}
//
//object CustomersTable : Table("customers") {
//    val id = varchar("id", 64).uniqueIndex()
//    val fullName = varchar("full_name", 255)
//    val birthDate = date("birth_date")
//    val age = integer("age")
//    override val primaryKey = PrimaryKey(id)
//}
//
//object PrescriptionsTable : Table("prescriptions") {
//    val id = varchar("id", 64).uniqueIndex()
//    val customerId = reference("customer_id", CustomersTable.id)
//    val pharmacistId = reference("pharmacist_id", PharmacistsTable.id)
//    val items = varchar("items", 255)
//    val createdAt = date("created_at").default(LocalDateTime.n)
//    override val primaryKey = PrimaryKey(id)
//}
//
//object PrescriptionItemsTable : Table("prescription_items") {
//    val id = varchar("id", 64).uniqueIndex()
//    val prescriptionId = reference("prescription_id", PrescriptionsTable.id)
//    val medicineId = reference("medicine_id", MedicinesTable.id)
//    val quantity = integer("quantity")
//    override val primaryKey = PrimaryKey(id)
//}