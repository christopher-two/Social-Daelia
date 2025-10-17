package org.christophertwo.daelia.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.christophertwo.daelia.core.ui.theme.socialTheme

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

}

@Preview
@Composable
private fun Preview() {
    socialTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}