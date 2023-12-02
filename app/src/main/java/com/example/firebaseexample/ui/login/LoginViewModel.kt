package com.example.firebaseexample.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseexample.service.interf.IAccountService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val iAccountService: IAccountService,
    //logService: LogService
) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(value: String) {
        uiState.value = uiState.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        uiState.value = uiState.value.copy(password = value)
    }

    fun onLoginClick(snackbar: Boolean = true, openAndPopUp: (String, String) -> Unit) {
//        if (!email.isValidEmail()) {
//            return
//        }
//
//        if (!password.isValidPassword()) {
//            return
//        }

        if (password != uiState.value.repeatPassword) {
            return
        }

        // Memulai menghubungkan akun apabila berhasil
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    Log.d("Snackbarr", "Berhasil")
                }

                //logService.logNonFatalCrash(throwable)
            },
            block = {
                iAccountService.linkAccount(email, password)
                //openAndPopUp(SETTINGS_SCREEN, SIGN_UP_SCREEN)
            }
        )
    }
}