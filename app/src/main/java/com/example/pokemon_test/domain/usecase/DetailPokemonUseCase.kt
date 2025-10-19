package com.example.pokemon_test.domain.usecase

import com.example.pokemon_test.data.remote.PokemonDetail
import com.example.pokemon_test.domain.model.User
import com.example.pokemon_test.domain.repository.PokemonRepository
import com.example.pokemon_test.domain.repository.UserRepository
import javax.inject.Inject

class DetailPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Result<PokemonDetail> {
        return repository.getDetailPokemon(name)
    }
}