package com.iwash.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.iwash.app.core.di.ServiceLocator

/** Equivalent of main.dart's entrypoint (WidgetsFlutterBinding + runApp(WashApp())). */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Equivalent of: SharedPreferences prefs -> token = prefs.getString('token')
        ServiceLocator.init(applicationContext)

        setContent {
            IwashApp()
        }
    }
}
