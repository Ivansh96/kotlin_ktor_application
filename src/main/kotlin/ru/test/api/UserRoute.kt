package ru.test.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.test.dal.model.user.UserModel
import ru.test.dal.model.user.getRole
import ru.test.request.user.UserLoginRequest
import ru.test.request.user.UserRegisterRequest
import ru.test.response.BaseResponse
import ru.test.service.user.UserService
import ru.test.util.Constants

fun Route.userRoute(userService: UserService) {

    post("api/v1/register") {
        val userRegisterRequest = call.receiveNullable<UserRegisterRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }
        try {
            val user = UserModel(
                id = 0,
                email = userRegisterRequest.email.trim().lowercase(),
                login = userRegisterRequest.login.trim().lowercase(),
                password = userRegisterRequest.password.trim().lowercase(),
                firstname = userRegisterRequest.firstname.trim(),
                lastname = userRegisterRequest.lastname.trim(),
                role = userRegisterRequest.role.trim().getRole()
            )
            userService.createUser(user)
            call.respond(HttpStatusCode.OK, BaseResponse(true, userService.generateToken(userModel = user)))

        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    post("api/v1/login") {
        val userLoginRequest = call.receiveNullable<UserLoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }
        try {
            val user = userService.getUserByEmail(userLoginRequest.email.trim().lowercase())
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.WRONG_EMAIL))
            } else {
                if (user.password == userLoginRequest.password) {
                    call.respond(HttpStatusCode.OK, BaseResponse(true, userService.generateToken(userModel = user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.INCORRECT_PASSWORD))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    delete("api/v1/delete-user") {
        val userToDeleteId = call.request.queryParameters[Constants.Value.ID]?.toInt() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@delete
        }
        try {
            userService.deleteUser(userId = userToDeleteId)
            call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.GENERAL))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    authenticate("jwt")  {
        println("inside 1st STEP")
        get("api/v1/get-user-info") {
            try {
                val user = call.principal<UserModel>()
                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.Conflict, BaseResponse(false, Constants.Error.USER_NOT_FOUND))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            }
        }
    }
}