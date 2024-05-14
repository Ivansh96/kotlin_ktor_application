package ru.test.request.card

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CardRegisterRequest(
    val id: Int? = null,
    val ownerId: Int,
    val title: String,
    val description: String,
    val creationDate: String,
    val isVerified: Boolean = false
)
