package com.example.pokemon_test.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon_test.presentation.viewmodel.DetailScreenViewModel
import com.example.pokemon_test.presentation.viewmodel.DetailUiState

@Composable
fun DetailScreen(
    pokemonName: String,
    navController: NavController,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setPokemonName(pokemonName)
    }

    DetailScreenView(
        uiState = uiState,
        pokemonName = pokemonName,
        onCLickBack = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenView(
    uiState: DetailUiState,
    pokemonName: String,
    onCLickBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detail Pokemon")
                },
                navigationIcon = {
                    IconButton(onClick = onCLickBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopStart
        ) {
            when (uiState) {
                is DetailUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is DetailUiState.Error -> {
                    Text(
                        text = uiState.message ?: "error..",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                is DetailUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp, start = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = pokemonName,
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = "Abilities :",
                            style = MaterialTheme.typography.headlineMedium
                        )

                        uiState.pokemon.abilities.forEach { abilityEntry ->
                            Text(
                                text = "- ${abilityEntry.ability.name}",
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreeniewPreview() {
    MaterialTheme {
        DetailScreenView(
            uiState = DetailUiState.Loading,
            pokemonName = "bulbasur",
            onCLickBack = {}
        )
    }
}