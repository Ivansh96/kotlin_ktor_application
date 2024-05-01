package ru.test

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.test.plugins.*
import ru.test.plugins.DatabaseFactory.dbInit

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    dbInit()
//    configureSecurity()
//    configureMonitoring()
//    configureSerialization()
//    configureDatabases()
//    configureRouting()
}
