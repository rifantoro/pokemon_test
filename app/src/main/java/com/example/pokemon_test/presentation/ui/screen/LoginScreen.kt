package com.example.pokemon_test.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon_test.Navigation
import com.example.pokemon_test.presentation.viewmodel.LoginUiState
import com.example.pokemon_test.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreenView(
        uiState = uiState,
        onLogin = { email, password ->
            viewModel.login(email, password)
        },
        onRegisterClick = {
            navController.navigate(Navigation.Register)
        },
        onLoginSuccess = {
            navController.navigate(Navigation.Home) {
                popUpTo(Navigation.Login) { inclusive = true }
            }
            viewModel.resetState()
        },
    )
}

@Composable
fun LoginScreenView(
    uiState: LoginUiState,
    onLogin: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                onLoginSuccess()
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Login", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = { onLogin(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            when (uiState) {
                is LoginUiState.Loading -> CircularProgressIndicator()
                is LoginUiState.Error -> Text(
                    uiState.message,
                    color = MaterialTheme.colorScheme.error
                )
                else -> {}
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onRegisterClick) {
                Text("Belum punyan akun? Register here")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreenView(
            uiState = LoginUiState.Idle,
            onLogin = { _, _ -> },
            onRegisterClick = {},
            onLoginSuccess = {}
        )
    }
}