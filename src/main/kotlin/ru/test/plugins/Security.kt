package ru.test.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import ru.test.service.user.UserService

fun Application.configureSecurity(service: UserService) {

    authentication {
        jwt("jwt") {
            verifier(service.getJwtVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").toString()
                val user = service.getUserByEmail(email = email)
                user
            }
        }
    }
}
