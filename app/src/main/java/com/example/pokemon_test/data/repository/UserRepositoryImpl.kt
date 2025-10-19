package com.example.pokemon_test.data.repository

import com.example.pokemon_test.data.local.UserDao
import com.example.pokemon_test.domain.model.User
import com.example.pokemon_test.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun registerUser(name: String, email: String, password: String) {
        val existingUser = userDao.getUserByEmail(email)
        if (existingUser != null) {
            throw IllegalArgumentException("Email already registered")
        }
        userDao.insertUser(User(name = name, email = email, password = password))
    }

    override suspend fun loginUser(email: String, password: String): User? {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    override suspend fun getCurrentUser(): User? {
        return userDao.getCurrentUser()
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}