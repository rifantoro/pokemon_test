package com.example.pokemon_test.data.remote

data class PokemonDetail(
    val abilities: List<AbilityEntry>
)

data class AbilityEntry(
    val ability: Ability,
    val isHidden: Boolean,
    val slot: Int
)

data class Ability(
    val name: String
)