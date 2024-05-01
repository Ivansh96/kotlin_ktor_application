package ru.test.dal.repository

import ru.test.dal.model.UserModel

interface UserRepository {

    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun addUser(userModel: UserModel)
}