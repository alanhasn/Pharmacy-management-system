//package infrastructure.persistence.exposed
//
//
//import org.jetbrains.exposed.sql.Database
//import org.jetbrains.exposed.sql.SchemaUtils
//import org.jetbrains.exposed.sql.transactions.transaction
//
//object DatabaseFactory {
//    fun init(sqlitePath: String = "pharmacy.db") {
//        val url = "jdbc:sqlite:$sqlitePath"
//        Database.connect(url, driver = "org.sqlite.JDBC")
//
//        transaction {
//            // Create the tables if they don't exist already in the database
//            SchemaUtils.create(
//                MedicinesTable,
//                InventoryTable,
//                PharmacistsTable,
//                CustomersTable,
//                PrescriptionsTable
//            )
//        }
//    }
//}
