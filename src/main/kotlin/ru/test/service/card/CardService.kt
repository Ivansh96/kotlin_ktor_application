package ru.test.service.card

import ru.test.dal.model.card.CardModel
import ru.test.dal.repository.CardRepository

class CardService(
    private val cardRepository: CardRepository
) {

    suspend fun createCard(cardModel: CardModel) = cardRepository.addCard(cardModel = cardModel)

    suspend fun getAllCards() = cardRepository.getAllCards()

    suspend fun updateCard(cardModel: CardModel) = cardRepository.updateCard(cardModel = cardModel)

    suspend fun deleteCard(cardId: Int) = cardRepository.deleteCard(cardId = cardId)

}