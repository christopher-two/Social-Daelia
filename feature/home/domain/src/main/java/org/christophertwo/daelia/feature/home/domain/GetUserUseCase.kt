package org.christophertwo.daelia.feature.home.domain

import org.christophertwo.daelia.profile.api.FirestoreRepository
import org.christophertwo.daelia.profile.api.UserFirestore

class GetUserUseCase(
    private val firestoreRepository: FirestoreRepository
) {
    suspend operator fun invoke(): UserFirestore? {
        return try {
            firestoreRepository.userProfile()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}