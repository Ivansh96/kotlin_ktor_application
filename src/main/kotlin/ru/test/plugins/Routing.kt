package ru.test.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.test.api.cardRoute
import ru.test.api.userRoute
import ru.test.service.card.CardService
import ru.test.service.user.UserService

fun Application.configureRouting(userService: UserService, cardService: CardService) {
    routing {
        userRoute(userService = userService)
        cardRoute(cardService = cardService)
    }
}
