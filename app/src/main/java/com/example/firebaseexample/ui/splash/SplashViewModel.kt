package com.example.firebaseexample.ui.splash

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseexample.LOGIN_SCREEN
import com.example.firebaseexample.SIGN_UP_SCREEN
import com.example.firebaseexample.SPLASH_SCREEN
import com.example.firebaseexample.STORIES_SCREEN
import com.example.firebaseexample.service.interf.IAccountService
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.launch

class SplashViewModel(
    private val iAccountService: IAccountService,
) : ViewModel() {
    val isError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        isError.value = false

        if (iAccountService.hasUser) openAndPopUp(STORIES_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(SIGN_UP_SCREEN, SPLASH_SCREEN) //createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch(
            block = {
                try {
                    iAccountService.createAnonymousAccount()

                    Log.d("abc","aa")
                } catch (ex: FirebaseAuthException) {
                    Log.d("exxxx",ex.toString())
                    throw ex
                }

                openAndPopUp (STORIES_SCREEN, SPLASH_SCREEN)
            }
        )
    }
}