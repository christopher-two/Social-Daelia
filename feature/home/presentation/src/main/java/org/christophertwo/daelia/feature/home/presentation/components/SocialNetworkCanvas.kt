package org.christophertwo.daelia.feature.home.presentation.components

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import org.christophertwo.daelia.feature.home.presentation.model.NetworkConnection
import org.christophertwo.daelia.feature.home.presentation.model.NetworkNode
import org.christophertwo.daelia.feature.home.presentation.utils.NetworkLayoutCalculator
import org.christophertwo.daelia.profile.api.UserFirestore

@Composable
fun SocialNetworkCanvas(
    mainUser: UserFirestore?,
    friends: List<UserFirestore>,
    availableUsers: List<UserFirestore>,
    onUserClick: (UserFirestore) -> Unit,
    modifier: Modifier = Modifier
) {
    var nodes by remember { mutableStateOf<List<NetworkNode>>(emptyList()) }
    var connections by remember { mutableStateOf<List<NetworkConnection>>(emptyList()) }
    var canvasSize by remember { mutableStateOf(0f to 0f) }

    // Estados para pan y zoom
    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }

    // Recalcular layout cuando cambian los amigos o usuarios disponibles
    LaunchedEffect(friends, availableUsers, canvasSize) {
        val (width, height) = canvasSize
        if (width > 0 && height > 0) {
            val (calculatedNodes, calculatedConnections) = NetworkLayoutCalculator.calculateNetworkLayout(
                mainUser = mainUser,
                friends = friends,
                availableUsers = availableUsers,
                canvasWidth = width,
                canvasHeight = height
            )
            nodes = calculatedNodes
            connections = calculatedConnections
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                if (size.width > 0 && size.height > 0) {
                    canvasSize = size.width.toFloat() to size.height.toFloat()
                }
            }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.5f, 3f)
                    offset = Offset(
                        x = offset.x + pan.x,
                        y = offset.y + pan.y
                    )
                }
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                )
        ) {
            // Dibujar líneas de conexión (solo si hay)
            if (connections.isNotEmpty()) {
                NetworkConnectionLines(
                    connections = connections
                )
            }

            // Dibujar nodos de usuarios
            nodes.forEach { node ->
                NetworkUserNode(
                    node = node,
                    onClick = if (!node.isMainUser) {
                        { onUserClick(node.user) }
                    } else {
                        null
                    }
                )
            }
        }
    }
}
