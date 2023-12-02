package com.example.firebaseexample

import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class FirebaseExampleAppState(
    val navController: NavHostController,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            Log.d("aaa", "aaa")
        }
    }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}
