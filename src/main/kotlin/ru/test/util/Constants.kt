package ru.test.util

class Constants {

    object Role {
        const val ADMIN = "admin"
        const val MODERATOR = "moderator"
        const val CLIENT = "client"
    }

    object Error {
        const val GENERAL = "Something went wrong!"
        const val WRONG_EMAIL = "Wrong email address!"
        const val INCORRECT_PASSWORD = "Incorrect password!"
        const val INCORRECT_FIELDS = "Incorrect fields!"
        const val USER_NOT_FOUND = "Error! user not found!"
     }

    object Success {
        const val GENERAL = "Successful action!"
    }

    object Value {
        const val ID = "id"
    }
}