package com.example.pokemon_test.domain.usecase

import com.example.pokemon_test.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String) {
        repository.registerUser(name, email, password)
    }
}