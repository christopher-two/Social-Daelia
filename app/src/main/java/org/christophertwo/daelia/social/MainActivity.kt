package org.christophertwo.daelia.social

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.firstOrNull
import org.christophertwo.daelia.core.common.RoutesStart
import org.christophertwo.daelia.core.ui.theme.socialTheme
import org.christophertwo.daelia.session.api.SessionRepository
import org.christophertwo.daelia.social.navigation.NavigationStart
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val splashViewModel: SplashViewModel = koinViewModel()
            val navController = rememberNavController()
            val sessionRepository = koinInject<SessionRepository>()
            var sessionIsLogin by remember { mutableStateOf<Boolean?>(null) }

            splashScreen.setKeepOnScreenCondition { splashViewModel.isLoading.value }

            LaunchedEffect(Unit) {
                sessionIsLogin = sessionRepository.isUserLoggedIn().firstOrNull()
            }

            socialTheme(
                content = {
                    when (sessionIsLogin) {
                        null -> NavigationStart(
                            navController = navController,
                            startDestination = RoutesStart.Login
                        )

                        true -> NavigationStart(
                            navController = navController,
                            startDestination = RoutesStart.Home
                        )

                        else -> NavigationStart(
                            navController = navController,
                            startDestination = RoutesStart.Login
                        )
                    }
                }
            )
        }
    }
}