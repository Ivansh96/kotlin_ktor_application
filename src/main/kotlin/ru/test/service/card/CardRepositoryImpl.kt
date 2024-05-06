package ru.test.service.card

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import ru.test.dal.model.card.CardModel
import ru.test.dal.repository.CardRepository
import ru.test.dal.table.CardTable
import ru.test.plugins.DatabaseFactory.dbQuery


class CardRepositoryImpl : CardRepository {

    override suspend fun addCard(cardModel: CardModel) {
        return dbQuery {
            CardTable.insert {
                ownerId.eq(cardModel.ownerId)
                title.eq(cardModel.title)
                description.eq(cardModel.description)
                creationDate.eq(cardModel.creationDate)
                isVerified.eq(cardModel.isVerified)
            }
        }
    }

    override suspend fun getAllCards(): List<CardModel> {
        return dbQuery {
            CardTable.selectAll()
                .mapNotNull { rowToCard(row = it) }
        }
    }

    override suspend fun updateCard(cardModel: CardModel) {
        return dbQuery {
            CardTable.update(
                where = {
                    CardTable.id.eq(cardModel.id)
                }
            ) {
                ownerId.eq(cardModel.ownerId)
                title.eq(cardModel.title)
                description.eq(cardModel.description)
                creationDate.eq(cardModel.creationDate)
                isVerified.eq(cardModel.isVerified)
            }
        }
    }

    override suspend fun deleteCard(cardId: Int) {
        return dbQuery {
            CardTable.deleteWhere {
                id.eq(cardId)
            }
        }
    }

    private fun rowToCard(row: ResultRow?): CardModel? {
        if (row == null) {
            return null
        }
        return CardModel(
            id = row[CardTable.id],
            ownerId = row[CardTable.ownerId],
            title = row[CardTable.title],
            description = row[CardTable.description],
            creationDate = row[CardTable.creationDate],
            isVerified = row[CardTable.isVerified]
        )
    }
}