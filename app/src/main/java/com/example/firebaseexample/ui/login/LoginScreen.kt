package com.example.firebaseexample.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.firebaseexample.ViewModelFactory

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance()
    )
) {
    val uiState by loginViewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = loginViewModel::onEmailChange,
        onPasswordChange = loginViewModel::onPasswordChange,
        onLoginClick = { loginViewModel.onLoginClick(openAndPopUp = openAndPopUp) },
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column {
        TextField(value = uiState.email, onValueChange = onEmailChange)
        TextField(value = uiState.password, onValueChange = onPasswordChange)

        Button(onClick = onLoginClick, modifier = modifier) {
            Text("Login")
        }
    }
}