package ru.test

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.test.authentification.JwtService
import ru.test.plugins.*
import ru.test.plugins.DatabaseFactory.dbInit
import ru.test.service.card.CardRepositoryImpl
import ru.test.service.card.CardService
import ru.test.service.user.UserRepositoryImpl
import ru.test.service.user.UserService

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val cardRepository = CardRepositoryImpl()
    val userService = UserService(userRepository, jwtService)
    val cardService = CardService(cardRepository)

    dbInit()
    configureSecurity(userService)
//    configureMonitoring()
//    configureSerialization()
//    configureDatabases()
//    configureRouting()
}
