package ru.test.dal.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UsersTable: Table() {

    val id: Column<Int> = integer("user_id").autoIncrement()
    val email: Column<String> = varchar("email", 50).uniqueIndex()
    val login: Column<String> = varchar("login", 50).uniqueIndex()
    val password: Column<String> = varchar("password", 50)
    val firstname: Column<String> = varchar("firstname", 50)
    val lastname: Column<String> = varchar("lastname", 50)
    val role: Column<String> = varchar("user_role", 20)
    val isActive: Column<Boolean> = bool("is_active")

    override val primaryKey: PrimaryKey = PrimaryKey(id);

}