package ru.test.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.test.authentification.hash
import ru.test.dal.model.user.UserModel
import ru.test.dal.model.user.getRole
import ru.test.request.user.LoginRequest
import ru.test.request.user.RegisterRequest
import ru.test.response.BaseResponse
import ru.test.service.user.UserService
import ru.test.util.Constants

fun Route.userRoute(userService: UserService) {

    val hashFun = { p: String -> hash(password = p) }

    post("api/v1/register") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }
        try {
            val user = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(),
                login = registerRequest.login.trim().lowercase(),
                password = registerRequest.password.trim().lowercase(),
                firstname = registerRequest.firstname.trim(),
                lastname = registerRequest.lastname.trim(),
                role = registerRequest.role.trim().getRole()
            )
            userService.createUser(user)
            call.respond(HttpStatusCode.OK, BaseResponse(true, userService.generateToken(userModel = user)))

        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    post("api/v1/login") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }
        try {
            val user = userService.getUserByEmail(loginRequest.email.trim().lowercase())
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.WRONG_EMAIL))
            } else {
                if (user.password == hashFun(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, userService.generateToken(userModel = user))
                } else {
                    call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.INCORRECT_PASSWORD))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }
}