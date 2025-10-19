package com.example.pokemon_test.domain.usecase

import com.example.pokemon_test.data.local.SessionManager
import com.example.pokemon_test.domain.model.User
import com.example.pokemon_test.domain.repository.UserRepository
import javax.inject.Inject

class HomeScreenUseCase @Inject constructor(
    private val sessionManager: SessionManager
) {
    suspend fun logout() {
        sessionManager.clearSession()
    }
}