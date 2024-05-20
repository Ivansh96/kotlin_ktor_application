package ru.test.request.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequest (
    val userId: Int? = null,
    val email: String,
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val isActive: Boolean = false,
    val role: String
)
