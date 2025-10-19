package com.example.pokemon_test.data.remote

data class Pokemon(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonData>
)

data class PokemonData(
    val name: String,
    val url: String
)