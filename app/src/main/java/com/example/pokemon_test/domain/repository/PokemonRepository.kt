package com.example.pokemon_test.domain.repository

import androidx.paging.PagingData
import com.example.pokemon_test.data.remote.PokemonData
import com.example.pokemon_test.data.remote.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonPager(
        pageSize: Int = 10,
        query: String? = null
    ): Flow<PagingData<PokemonData>>

    suspend fun getDetailPokemon(name: String): Result<PokemonDetail>
}