package ru.test.dal.model.user

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Int,
    val email: String,
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val isActive: Boolean = false,
    val role: RoleModel

) : Principal
