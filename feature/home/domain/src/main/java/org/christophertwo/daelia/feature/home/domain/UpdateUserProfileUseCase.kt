package org.christophertwo.daelia.feature.home.domain

import org.christophertwo.daelia.profile.api.FirestoreRepository
import org.christophertwo.daelia.profile.api.UserFirestore

class UpdateUserProfileUseCase(
    val firestoreRepository: FirestoreRepository
) {
    suspend operator fun invoke(
        userFirestore: UserFirestore
    ) {
        try {
            firestoreRepository.updateUserProfile(userFirestore)
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("Error updating user profile")
        }
    }
}