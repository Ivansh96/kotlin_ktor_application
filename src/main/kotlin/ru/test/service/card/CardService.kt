package ru.test.service.card

import ru.test.dal.model.card.CardModel

class CardService(
    private val cardRepositoryImpl: CardRepositoryImpl
) {

    suspend fun createCard(cardModel: CardModel) = cardRepositoryImpl.addCard(cardModel = cardModel)

    suspend fun getAllCards() = cardRepositoryImpl.getAllCards()

    suspend fun updateCard(cardModel: CardModel) = cardRepositoryImpl.updateCard(cardModel = cardModel)

    suspend fun deleteCard(cardId: Int) = cardRepositoryImpl.deleteCard(cardId = cardId)

}