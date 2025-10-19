@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.pokemon_test.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pokemon_test.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeTabViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    private val currentQuery = MutableStateFlow<String?>(null)

    val pokemons = currentQuery.flatMapLatest { query ->
        repository.getPokemonPager(pageSize = 10, query = query)
    }.cachedIn(viewModelScope)

    fun search(query: String) {
        currentQuery.value = query.ifBlank { null }
    }

}