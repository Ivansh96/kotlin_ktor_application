package ru.test.dal.model.card

import java.time.LocalDate

data class CardModel(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val description: String,
    val creationDate: String,
    val isVerified: Boolean = false
)
