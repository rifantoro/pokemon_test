package com.example.pokemon_test.domain.repository

import com.example.pokemon_test.domain.model.User

interface UserRepository {
    suspend fun registerUser(name: String, email: String, password: String)
    suspend fun loginUser(email: String, password: String): User?
    suspend fun getUserByEmail(email: String): User?

}