package org.christophertwo.daelia.feature.home.domain

import org.christophertwo.daelia.profile.api.FirestoreRepository
import org.christophertwo.daelia.profile.api.UserFirestore

class GetFriendsUseCase(
    private val firestoreRepository: FirestoreRepository
) {
    suspend operator fun invoke(): List<UserFirestore> {
        return try {
            firestoreRepository.getFriends()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
