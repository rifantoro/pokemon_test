package com.example.pokemon_test.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pokemon_test.Navigation
import com.example.pokemon_test.presentation.viewmodel.HomeTabViewModel
import com.example.pokemon_test.presentation.viewmodel.ProfileTabUiState
import com.example.pokemon_test.presentation.viewmodel.ProfileTabViewModel

@Composable
fun HomeScreen(
    navController: NavController
) {
    HomeScreenView(
        onDetailClick = { pokemonName ->
            navController.navigate(
                Navigation.Details(namePokemon = pokemonName)
            )
        },
        onClickLogout = {
            navController.navigate(Navigation.Login) {
                popUpTo(Navigation.Home) { inclusive = true }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenView(
    onDetailClick: (String) -> Unit,
    onClickLogout: () -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Home", "Profile")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pokemon") }) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            PrimaryTabRow(
                selectedTabIndex = selectedTabIndex,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> HomeTab (onDetailClick = onDetailClick)
                1 -> ProfileTab(onClickLogout = onClickLogout)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTab(
    viewModel: HomeTabViewModel = hiltViewModel(),
    onDetailClick: (String) -> Unit
) = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true)}
    val pokemons = viewModel.pokemons.collectAsLazyPagingItems()

    Column {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = {
                        query = it
                        viewModel.search(it)
                    },
                    onSearch = {
                        active = false
                    },
                    expanded = active,
                    onExpandedChange = { active = it },
                    placeholder = { Text("Search pokemon...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            IconButton(onClick = { query = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear")
                            }
                        }
                    },
                )
            },
            expanded = false,
            onExpandedChange = { active },
            modifier = Modifier.fillMaxWidth()
        ) {

        }

        Box(modifier = Modifier.padding(16.dp)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(pokemons.itemCount) { index ->
                    pokemons[index]?.let { pokemon ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onDetailClick(pokemon.name)
                                }
                        ) {
                            Text(
                                text = pokemon.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                            HorizontalDivider()
                        }
                    }
                }

                pokemons.apply {
                    when {
                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileTab(
    profileTabViewModel: ProfileTabViewModel = hiltViewModel(),
    onClickLogout: () -> Unit
) = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

    val uiState by profileTabViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        profileTabViewModel.getData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        when (val state = uiState) {
            is ProfileTabUiState.Loading -> {
                CircularProgressIndicator()
            }
            is ProfileTabUiState.Error -> {
                Text(state.message, color = MaterialTheme.colorScheme.error)
            }
            is ProfileTabUiState.Success -> {
                Text(
                    text = "Email :",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = state.user.email,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Name :",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = state.user.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Button(onClick = onClickLogout) {
                    Text("Logout")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenViewPreview() {
    MaterialTheme {
        HomeScreenView(
            onDetailClick = {

            },
            onClickLogout = {

            }
        )
    }
}