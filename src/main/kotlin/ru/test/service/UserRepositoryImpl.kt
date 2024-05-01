package ru.test.service

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import ru.test.dal.model.UserModel
import ru.test.dal.model.getRole
import ru.test.dal.model.getString
import ru.test.dal.repository.UserRepository
import ru.test.dal.table.UsersTable
import ru.test.plugins.DatabaseFactory.dbQuery

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserByEmail(email: String): UserModel? {
        return dbQuery {
            UsersTable.select { UsersTable.email.eq(email) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun addUser(userModel: UserModel) {
        return dbQuery {
            UsersTable.insert {
                email.eq(userModel.email)
                login.eq(userModel.login)
                firstname.eq(userModel.firstname)
                lastname.eq(userModel.lastname)
                isActive.eq(userModel.isActive)
                role.eq(userModel.role.getString())
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null) {
            return null
        }
        return UserModel(
            id = row[UsersTable.id],
            email = row[UsersTable.email],
            login = row[UsersTable.login],
            password = row[UsersTable.password],
            firstname = row[UsersTable.firstname],
            lastname = row[UsersTable.lastname],
            isActive = row[UsersTable.isActive],
            role = row[UsersTable.role].getRole()
        )
    }
}