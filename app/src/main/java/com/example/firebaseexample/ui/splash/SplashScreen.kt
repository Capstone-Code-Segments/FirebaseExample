package com.example.firebaseexample.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebaseexample.ViewModelFactory
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: SplashViewModel = viewModel(factory = ViewModelFactory.getInstance())
) {
    SplashScreenContent(
        onAppStart = { viewModel.onAppStart(openAndPopUp) },
        isError = viewModel.isError.value
    )
}

@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit,
    isError: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isError) {

        } else {
            //CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
        }

        LaunchedEffect(true) {
            delay(1000L)
            onAppStart()
        }
    }
}