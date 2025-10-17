package org.christophertwo.daelia.feature.home.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.christophertwo.daelia.core.ui.theme.socialTheme
import org.christophertwo.daelia.feature.home.presentation.components.AddFriendDialog
import org.christophertwo.daelia.feature.home.presentation.components.SocialNetworkCanvas
import org.christophertwo.daelia.profile.api.UserFirestore

@Composable
fun HomeRoot(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        SocialNetworkCanvas(
            mainUser = state.user,
            friends = state.friends,
            availableUsers = state.availableUsers,
            onUserClick = { user ->
                onAction(HomeAction.OnUserClick(user))
            }
        )

        // Mostrar diálogo de agregar amigo
        state.selectedUserForDialog?.let { user ->
            AddFriendDialog(
                user = user,
                onConfirm = {
                    onAction(HomeAction.AddFriend(user))
                },
                onDismiss = {
                    onAction(HomeAction.DismissDialog)
                }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    socialTheme {
        HomeScreen(
            state = HomeState(
                user = UserFirestore(
                    name = "Usuario Principal",
                    imageUrl = "https://i.pravatar.cc/150?img=50"
                ),
                friends = getDummyFriends()
            ),
            onAction = {}
        )
    }
}