package com.example.pokemon_test.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon_test.data.remote.PokemonApi
import com.example.pokemon_test.data.remote.PokemonData

class PokemonPagingSource(
    private val api: PokemonApi,
    private val pageSize: Int = 10,
    private val query: String? = null
) : PagingSource<Int, PokemonData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonData> {
        val offset = params.key ?: 0
        val limit = pageSize

        return try {
            val response = api.getListPokemon(limit, if (offset == 0) 0 else offset + pageSize)
            Log.d("offset",offset.toString())
            val allPokemons = response.results

            val filtered = if (query.isNullOrBlank()) allPokemons
            else allPokemons.filter { it.name.contains(query, ignoreCase = true) }

            val prevKey = if (offset == 0) null else offset - pageSize
            val nextKey = if (filtered.isEmpty() || response.next == null) null else offset + pageSize
            Log.d("nextKey",nextKey.toString())

            LoadResult.Page(
                data = filtered,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonData>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(pageSize) ?: anchorPage?.nextKey?.minus(pageSize)
        }
    }
}