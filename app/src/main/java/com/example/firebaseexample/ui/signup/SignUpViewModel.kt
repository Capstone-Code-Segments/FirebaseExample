package com.example.firebaseexample.ui.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseexample.LOGIN_SCREEN
import com.example.firebaseexample.SIGN_UP_SCREEN
import com.example.firebaseexample.service.interf.IAccountService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class SignUpViewModel constructor(
    private val iAccountService: IAccountService,
    //logService: LogService
) : ViewModel() {
    var uiState = mutableStateOf(SignUpUiState())
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

    fun onRepeatPasswordChange(value: String) {
        uiState.value = uiState.value.copy(repeatPassword = value)
    }

    // Setelah tombol "Sign up" diklik, fungsi ini akan dijalankan
    fun onSignUpClick(snackbar: Boolean = true, openAndPopUp: (String, String) -> Unit) {
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
            block = {
                iAccountService.register(email, password)
                openAndPopUp(LOGIN_SCREEN, SIGN_UP_SCREEN)
            }
        )
    }
}