package com.example.pokemon_test.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getListPokemon(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Pokemon

    @GET("pokemon/{name}")
    suspend fun getDetailPokemon(
        @Path("name") name: String
    ) : PokemonDetail
}