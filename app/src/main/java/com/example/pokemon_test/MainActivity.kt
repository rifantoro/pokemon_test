package com.example.pokemon_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pokemon_test.presentation.ui.screen.DetailScreen
import com.example.pokemon_test.presentation.ui.screen.HomeScreen
import com.example.pokemon_test.presentation.ui.screen.LoginScreen
import com.example.pokemon_test.presentation.ui.screen.RegisterScreen
import com.example.pokemon_test.presentation.ui.screen.SplashScreen
import com.example.pokemon_test.presentation.ui.theme.Pokemon_testTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pokemon_testTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navigation.Splash) {
        composable<Navigation.Login> {
            LoginScreen(navController = navController)
        }

        composable<Navigation.Register> {
            RegisterScreen(navController = navController)
        }

        composable<Navigation.Splash> {
            SplashScreen(navController = navController)
        }

        composable<Navigation.Home> {
            HomeScreen(navController = navController)
        }

        composable<Navigation.Details> { backStackEntry ->
            val detailsObject = backStackEntry.toRoute<Navigation.Details>()
            val pokemonName = detailsObject.namePokemon
            DetailScreen(navController = navController, pokemonName = pokemonName)
        }
    }
}