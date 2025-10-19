package com.example.pokemon_test.domain.usecase

import com.example.pokemon_test.domain.model.User
import com.example.pokemon_test.domain.repository.UserRepository
import javax.inject.Inject

class ProfileTabUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String): User? {
        return repository.getUserByEmail(email)
    }
}