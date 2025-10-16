package org.christophertwo.daelia.core.common

import kotlinx.serialization.Serializable

@Serializable
sealed class RoutesHome {
    @Serializable
    object Feed : RoutesHome()

    @Serializable
    object Profile : RoutesHome()
}