@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.pokemon_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon_test.data.remote.PokemonDetail
import com.example.pokemon_test.domain.usecase.DetailPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val getDetailPokemonUseCase: DetailPokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    fun setPokemonName(pokemonName: String) {
        if (pokemonName.isBlank()) {
            _uiState.value = DetailUiState.Error("Pokemon name not found.")
        } else {
            viewModelScope.launch {
                _uiState.value = DetailUiState.Loading
                val result = getDetailPokemonUseCase(pokemonName)

                if (result.isSuccess) {
                    _uiState.value = DetailUiState.Success(result.getOrThrow())
                } else {
                    _uiState.value = DetailUiState.Error(result.exceptionOrNull()?.message)
                }
            }
        }
    }
}

sealed interface DetailUiState {
    data object Loading : DetailUiState
    data class Success(val pokemon: PokemonDetail) : DetailUiState
    data class Error(val message: String?) : DetailUiState
}