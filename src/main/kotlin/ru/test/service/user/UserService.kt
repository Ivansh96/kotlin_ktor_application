package ru.test.service.user

import com.auth0.jwt.JWTVerifier
import ru.test.authentification.JwtService
import ru.test.dal.model.user.UserModel
import ru.test.dal.repository.UserRepository

class UserService(
    private val repositoryImpl: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) = repositoryImpl.addUser(userModel = userModel)

    suspend fun getUserByEmail(email: String) = repositoryImpl.getUserByEmail(email = email)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(userModel = userModel)

    fun getJwtVerifier(): JWTVerifier = jwtService.getVerifier()
}