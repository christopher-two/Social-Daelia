package org.christophertwo.daelia.feature.login.presentation

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Globe
import org.christophertwo.daelia.core.common.RoutesStart
import org.christophertwo.daelia.core.ui.components.ContinueWhitGoogle
import org.christophertwo.daelia.core.ui.theme.socialTheme

@Composable
fun LoginRoot(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        navController = navController,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    navController: NavController,
    onAction: (LoginAction) -> Unit,
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result -> onAction(LoginAction.ContinueWhitGoogle(result)) }
    )

    LaunchedEffect(key1 = state.isLoggedIn) {
        if (state.isLoggedIn) {
            navController.navigate(RoutesStart.Home) {
                popUpTo(RoutesStart.Login) {
                    inclusive = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            content = {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Globe,
                    contentDescription = null,
                    tint = colorScheme.primary,
                    modifier = Modifier.height(100.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    color = colorScheme.surfaceContainer,
                    contentColor = colorScheme.onSurface,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    content = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            ContinueWhitGoogle(
                                onClick = {
                                    state.googleSignInClient?.signInIntent.let { input ->
                                        launcher.launch(input = input ?: Intent())
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                )
            }
        )

        // Overlay de carga cuando se está autenticando
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorScheme.surface.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        color = colorScheme.primary
                    )
                    Text(
                        text = "Iniciando sesión...",
                        color = colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    socialTheme {
        LoginScreen(
            state = LoginState(),
            navController = NavController(LocalContext.current),
            onAction = {}
        )
    }
}