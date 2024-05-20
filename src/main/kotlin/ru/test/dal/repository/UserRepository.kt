package ru.test.dal.repository

import ru.test.dal.model.user.UserModel

interface UserRepository {

    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun addUser(userModel: UserModel)

    suspend fun deleteUser(userId: Int)
}