package org.christophertwo.daelia.feature.home.presentation.model

import androidx.compose.ui.geometry.Offset

/**
 * Representa una conexión entre dos nodos en la red
 */
data class NetworkConnection(
    val start: Offset,
    val end: Offset
)

