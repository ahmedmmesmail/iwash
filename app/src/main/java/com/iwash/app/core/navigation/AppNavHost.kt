package com.iwash.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iwash.app.feature.home.presentation.HomeScreen
import com.iwash.app.feature.register.presentation.RegisterScreen

/** Equivalent of AppRouter (core/routes/app_router.dart). */
@Composable
fun AppNavHost(
    startDestination: String,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppRoutes.REGISTER_SCREEN) {
            RegisterScreen(
                onBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(AppRoutes.HOME_SCREEN) {
                        popUpTo(0)
                    }
                },
            )
        }
        composable(AppRoutes.HOME_SCREEN) {
            HomeScreen()
        }
    }
}
