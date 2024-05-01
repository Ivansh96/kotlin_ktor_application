package ru.test.dal.model

import io.ktor.server.auth.*

data class UserModel(
    val id: Int,
    val email: String,
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val isActive: Boolean,
    val role: RoleModel

) : Principal
