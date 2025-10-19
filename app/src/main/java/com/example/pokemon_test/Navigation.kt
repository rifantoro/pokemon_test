package com.example.pokemon_test

import kotlinx.serialization.Serializable

sealed class Navigation {
    @Serializable
    object Splash : Navigation()

    @Serializable
    object Login : Navigation()

    @Serializable
    object Register : Navigation()

    @Serializable
    object Home : Navigation()

    @Serializable
    data class Details(val namePokemon: String) : Navigation()
}