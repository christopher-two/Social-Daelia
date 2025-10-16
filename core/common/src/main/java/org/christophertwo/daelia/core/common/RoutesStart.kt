package org.christophertwo.daelia.core.common

import kotlinx.serialization.Serializable

@Serializable
sealed class RoutesStart {
    @Serializable
    object Login : RoutesStart()

    @Serializable
    object Home : RoutesStart()
}