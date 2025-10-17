package org.christophertwo.daelia.profile.api

import kotlinx.serialization.Serializable

@Serializable
data class UserFirestore(
    val name: String? = null,
    val imageUrl: String? = null,
    val friends: List<UserFirestore>? = null,
)
