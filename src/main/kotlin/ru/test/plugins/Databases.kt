package ru.test.plugins

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import ru.test.dal.model.table.CardTable
import ru.test.dal.model.table.UsersTable


object DatabaseFactory {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbDriver = System.getenv("DB_POSTGRES_DRIVER")
    private val dbUrl = System.getenv("DB_POSTGRES_URL")
    private val dbUser = System.getenv("DB_POSTGRES_USER")
    private val dbPassword = System.getenv("DB_PASSWORD")

    fun Application.dbInit() {
        Database.connect(getHikariDataSource())

        transaction {
            SchemaUtils.create(
                UsersTable, CardTable
            )
        }
    }


    private fun getHikariDataSource(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = dbDriver
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 5
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }
}
