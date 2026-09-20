package com.iwash.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.iwash.app.core.di.ServiceLocator
import com.iwash.app.core.navigation.AppNavHost
import com.iwash.app.core.navigation.AppRoutes

/** Equivalent of wash_app.dart. */
@Composable
fun IwashApp() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val startDestination = if (ServiceLocator.tokenManager.cachedToken != null) {
                AppRoutes.HOME_SCREEN
            } else {
                AppRoutes.REGISTER_SCREEN
            }
            AppNavHost(startDestination = startDestination)
        }
    }
}
