package org.christophertwo.daelia.social.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.christophertwo.daelia.core.common.RoutesStart
import org.christophertwo.daelia.feature.login.presentation.LoginRoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationStart(
    navController: NavHostController,
    startDestination: RoutesStart,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<RoutesStart.Login> {
            LoginRoot(
                viewModel = koinViewModel(),
                navController = navController
            )
        }
        composable<RoutesStart.Home> {

        }
    }
}