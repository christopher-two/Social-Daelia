package org.christophertwo.daelia.feature.home.presentation

import org.christophertwo.daelia.profile.api.UserFirestore

data class HomeState(
    val user: UserFirestore? = null
)