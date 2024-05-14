package ru.test.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.test.dal.model.card.CardModel
import ru.test.request.card.CardRegisterRequest
import ru.test.response.BaseResponse
import ru.test.service.card.CardService
import ru.test.util.Constants

fun Route.cardRoute(cardService: CardService) {

    get("api/v1/cards") {
        try {
            val cards = cardService.getAllCards()
            call.respond(HttpStatusCode.OK, cards)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    authenticate("jwt") {
        post("api/v1/register-card") {
            val cardRegisterRequest = call.receiveNullable<CardRegisterRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.INCORRECT_FIELDS))
                return@post
            }
            try {
                val card = CardModel(
                    id = 0,
                    ownerId = cardRegisterRequest.ownerId,
                    title = cardRegisterRequest.title.trim().lowercase(),
                    description = cardRegisterRequest.description.trim().lowercase(),
                    creationDate = cardRegisterRequest.creationDate,
                    isVerified = cardRegisterRequest.isVerified
                )
                cardService.createCard(card)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.GENERAL))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        post("api/v1/update-card") {
            val cardRegisterRequest = call.receiveNullable<CardRegisterRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.INCORRECT_FIELDS))
                return@post
            }
            try {
                val card = CardModel(
                    id = 0,
                    ownerId = cardRegisterRequest.ownerId,
                    title = cardRegisterRequest.title.trim().lowercase(),
                    description = cardRegisterRequest.description.trim().lowercase(),
                    creationDate = cardRegisterRequest.creationDate,
                    isVerified = cardRegisterRequest.isVerified
                )
                cardService.updateCard(card)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.GENERAL))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        delete("api/v1/delete-card") {
            val cardRegisterRequest = call.receiveNullable<CardRegisterRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.INCORRECT_FIELDS))
                return@delete
            }
            try {
                cardService.deleteCard(cardId = cardRegisterRequest.id ?: 0)
                call.respond(HttpStatusCode.OK, BaseResponse(true, Constants.Success.GENERAL))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
            }
        }
    }
}