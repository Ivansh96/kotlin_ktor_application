package ru.test.service.user

import com.auth0.jwt.JWTVerifier
import ru.test.authentification.JwtService
import ru.test.dal.model.user.UserModel
import ru.test.dal.repository.UserRepository

class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) = userRepository.addUser(userModel = userModel)

    suspend fun getUserByEmail(email: String) = userRepository.getUserByEmail(email = email)

    suspend fun deleteUser(userId: Int) = userRepository.deleteUser(userId = userId)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(userModel = userModel)

    fun getJwtVerifier(): JWTVerifier = jwtService.getVerifier()
}