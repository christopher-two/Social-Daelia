package org.christophertwo.daelia.feature.home.presentation.model

import androidx.compose.ui.geometry.Offset
import org.christophertwo.daelia.profile.api.UserFirestore

/**
 * Representa un nodo en la red social
 */
data class NetworkNode(
    val user: UserFirestore,
    val position: Offset,
    val isMainUser: Boolean = false
)

