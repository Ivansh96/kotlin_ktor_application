package ru.test.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequest (
    val email: String,
    val password: String
)
