package com.example.pokemon_test.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_test.data.local.SessionManager
import com.example.pokemon_test.domain.model.User
import com.example.pokemon_test.domain.usecase.ProfileTabUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileTabViewModel @Inject constructor(
    private val profileTabUseCase: ProfileTabUseCase,
    @ApplicationContext context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow<ProfileTabUiState>(ProfileTabUiState.Loading)
    val uiState: StateFlow<ProfileTabUiState> = _uiState

    private val sessionManager = SessionManager(context)

    fun getData() {
        val userEmail: String? = sessionManager.getEmail()
        viewModelScope.launch {
            _uiState.value = ProfileTabUiState.Loading
            try {
                if (userEmail.isNullOrBlank()) {
                    _uiState.value = ProfileTabUiState.Error("No email found in session")
                    return@launch
                }

                val user = profileTabUseCase(userEmail)
                user?.let {
                    _uiState.value = ProfileTabUiState.Success(it)
                }
            } catch (e: Exception) {
                _uiState.value = ProfileTabUiState.Error(e.message ?: "Failed to get data")
            }
        }
    }
}

sealed class ProfileTabUiState {
    object Loading : ProfileTabUiState()
    data class Success(val user: User) : ProfileTabUiState()
    data class Error(val message: String) : ProfileTabUiState()
}