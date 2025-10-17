package org.christophertwo.daelia.feature.home.presentation.components

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
    modifier: Modifier = Modifier
) {
    var nodes by remember { mutableStateOf<List<NetworkNode>>(emptyList()) }
    var connections by remember { mutableStateOf<List<NetworkConnection>>(emptyList()) }

    // Estados para pan y zoom
    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                if (size.width > 0 && size.height > 0) {
                    // Decidir qué layout usar según si hay amigos o usuarios disponibles
                    val (calculatedNodes, calculatedConnections) = if (friends.isNotEmpty()) {
                        // Si hay amigos, mostrar red con conexiones
                        NetworkLayoutCalculator.calculateNetworkLayout(
                            mainUser = mainUser,
                            friends = friends,
                            canvasWidth = size.width.toFloat(),
                            canvasHeight = size.height.toFloat()
                        )
                    } else {
                        // Si no hay amigos, mostrar usuarios disponibles SIN conexiones
                        NetworkLayoutCalculator.calculateAvailableUsersLayout(
                            mainUser = mainUser,
                            availableUsers = availableUsers,
                            canvasWidth = size.width.toFloat(),
                            canvasHeight = size.height.toFloat()
                        )
                    }
                    nodes = calculatedNodes
                    connections = calculatedConnections
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
                NetworkUserNode(node = node)
            }
        }
    }
}
