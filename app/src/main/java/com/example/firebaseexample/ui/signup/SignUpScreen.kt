package com.example.firebaseexample.ui.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.firebaseexample.ViewModelFactory

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    signUpViewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory.getInstance()
    )
) {
    val uiState by signUpViewModel.uiState

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = signUpViewModel::onEmailChange,
        onPasswordChange = signUpViewModel::onPasswordChange,
        onRepeatPasswordChange = signUpViewModel::onRepeatPasswordChange,
        onSignUpClick = { signUpViewModel.onSignUpClick(openAndPopUp = openAndPopUp) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = uiState.email,
            onValueChange = onEmailChange,
            placeholder = @Composable {
                Text(
                    text = "Insert your-email"
                )
            },)
        TextField(value = uiState.password, onValueChange = onPasswordChange)
        TextField(value = uiState.repeatPassword, onValueChange = onRepeatPasswordChange)

        Button(onClick = onSignUpClick, modifier = modifier) {
            Text("Sign up")
        }
    }
}