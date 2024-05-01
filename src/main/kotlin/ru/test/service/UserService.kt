package ru.test.service

import ru.test.authentification.JwtService
import ru.test.dal.model.UserModel
import ru.test.dal.repository.UserRepository

class UserService(
    private val repositoryImpl: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) = repositoryImpl.addUser(userModel = userModel)

    suspend fun getUserByEmail(email: String) = repositoryImpl.getUserByEmail(email = email)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(userModel = userModel)
}