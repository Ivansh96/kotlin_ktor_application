package ru.test.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.runBlocking
import ru.test.authentification.JwtService
import ru.test.dal.model.RoleModel
import ru.test.dal.model.UserModel
import ru.test.service.UserRepositoryImpl
import ru.test.service.UserService

fun Application.configureSecurity() {

    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val service = UserService(repository, jwtService)

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
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
