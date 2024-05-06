package ru.test.dal.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

object CardTable: Table() {

    val id: Column<Int> = integer("card_id").autoIncrement()
    val ownerId: Column<Int> = integer("owner_id").references(UsersTable.id)
    val title: Column<String> = varchar("title", 100)
    val description: Column<String> = varchar("description", 1000)
    val creationDate: Column<LocalDate> = date("creation_date")
    val isVerified: Column<Boolean> = bool("is_verified")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}