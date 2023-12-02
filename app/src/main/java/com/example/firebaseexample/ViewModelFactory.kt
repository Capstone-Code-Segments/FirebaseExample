package com.example.firebaseexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebaseexample.ui.login.LoginViewModel
import com.example.firebaseexample.ui.signup.SignUpViewModel
import com.example.firebaseexample.ui.splash.SplashViewModel
import com.example.firebaseexample.ui.stories.StoriesViewModel

class ViewModelFactory private constructor() :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory()
            }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(Injection.provideAccountService()) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(Injection.provideAccountService()) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideAccountService()) as T
            }
            modelClass.isAssignableFrom(StoriesViewModel::class.java) -> {
                StoriesViewModel(Injection.provideStoryService()) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}