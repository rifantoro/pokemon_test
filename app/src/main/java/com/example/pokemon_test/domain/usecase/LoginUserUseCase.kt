package com.example.pokemon_test.domain.usecase

import com.example.pokemon_test.domain.model.User
import com.example.pokemon_test.domain.repository.UserRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): User? {
        return repository.loginUser(email, password)
    }
}