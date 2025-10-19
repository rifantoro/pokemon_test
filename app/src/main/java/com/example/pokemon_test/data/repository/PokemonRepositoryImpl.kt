package com.example.pokemon_test.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon_test.data.paging.PokemonPagingSource
import com.example.pokemon_test.data.remote.PokemonApi
import com.example.pokemon_test.data.remote.PokemonData
import com.example.pokemon_test.data.remote.PokemonDetail
import com.example.pokemon_test.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi
) : PokemonRepository {

    override fun getPokemonPager(
        pageSize: Int,
        query: String?
    ): Flow<PagingData<PokemonData>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PokemonPagingSource(api, pageSize, query)
            }
        ).flow
    }

    override suspend fun getDetailPokemon(name: String): Result<PokemonDetail> {
        return try {
            val detail = api.getDetailPokemon(name = name)
            Result.success(detail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}