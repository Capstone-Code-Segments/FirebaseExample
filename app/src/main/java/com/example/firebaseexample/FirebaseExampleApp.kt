package com.example.firebaseexample

import android.Manifest
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseexample.ui.login.LoginScreen
import com.example.firebaseexample.ui.signup.SignUpScreen
import com.example.firebaseexample.ui.splash.SplashScreen
import com.example.firebaseexample.ui.stories.StoriesScreen
import com.example.firebaseexample.ui.theme.FirebaseExampleTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseExampleApp() {
    /**
     * Meminta izin akses untuk notifikasi pada Android 13 (Tiramisu)
     */
    FirebaseExampleTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState()

            Scaffold(
//                snackbarHost = {
//                    SnackbarHost(
//                        hostState = it,
//                        modifier = Modifier.padding(8.dp),
//                        snackbar = { snackbarData ->
//                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
//                        }
//                    )
//                },
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    makeItSoGraph(appState)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
//        if (permissionState.status.shouldShowRationale) RationaleDialog()
//        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

@SuppressLint("RememberReturnType")
@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(navController, resources, coroutineScope) {
        FirebaseExampleAppState(navController, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

fun NavGraphBuilder.makeItSoGraph(appState: FirebaseExampleAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SETTINGS_SCREEN) {
//        SettingsScreen(
//            restartApp = { route -> appState.clearAndNavigate(route) },
//            openScreen = { route -> appState.navigate(route) }
//        )
    }

    composable(LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(STORIES_SCREEN) {
        StoriesScreen(openScreen = { route -> appState.navigate(route) })
        //TasksScreen(openScreen = { route -> appState.navigate(route) }) }
    }

//    composable(
//        route = "$EDIT_TASK_SCREEN$STORY_ID_ARG",
//        arguments = listOf(navArgument(STORY_ID) {
//            nullable = true
//            defaultValue = null
//        })
//    ) {
//        EditStoryScreen(
//            popUpScreen = { appState.popUp() }
//        )
//    }
}