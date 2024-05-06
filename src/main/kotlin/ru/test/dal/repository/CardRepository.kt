package ru.test.dal.repository

import ru.test.dal.model.card.CardModel

interface CardRepository {

    suspend fun addCard(cardModel: CardModel)

    suspend fun getAllCards(): List<CardModel>

    suspend fun updateCard(cardModel: CardModel)

    suspend fun deleteCard(cardId: Int)
}