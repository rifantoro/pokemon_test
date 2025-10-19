@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.pokemon_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokemon_test.domain.repository.PokemonRepository
import com.example.pokemon_test.domain.usecase.HomeScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val homeScreenUseCase: HomeScreenUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            homeScreenUseCase.logout()
        }
    }

}