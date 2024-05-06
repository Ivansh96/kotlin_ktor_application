package ru.test.dal.model.user

import ru.test.util.Constants

enum class RoleModel {
    ADMIN, MODERATOR, CLIENT
}

fun String.getRole(): RoleModel {
    return when (this) {
        Constants.Role.ADMIN -> RoleModel.ADMIN
        Constants.Role.MODERATOR -> RoleModel.MODERATOR
        else -> RoleModel.CLIENT
    }
}

fun RoleModel.getString(): String {
    return when(this) {
        RoleModel.ADMIN -> Constants.Role.ADMIN
        RoleModel.MODERATOR -> Constants.Role.MODERATOR
        else -> Constants.Role.CLIENT
    }
}