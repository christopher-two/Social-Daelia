package org.christophertwo.daelia.feature.home.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import org.christophertwo.daelia.feature.home.presentation.model.NetworkConnection

@Composable
fun NetworkConnectionLines(
    connections: List<NetworkConnection>,
    modifier: Modifier = Modifier
) {
    val normalLineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
    val mutualFriendColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)

    Canvas(modifier = modifier.fillMaxSize()) {
        connections.forEach { connection ->
            drawLine(
                color = if (connection.isMutualFriend) mutualFriendColor else normalLineColor,
                start = connection.start,
                end = connection.end,
                strokeWidth = if (connection.isMutualFriend) 3f else 2f,
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }
    }
}
