package org.christophertwo.daelia.feature.home.domain

import org.christophertwo.daelia.profile.api.FirestoreRepository
import org.christophertwo.daelia.profile.api.UserFirestore

class GetAvailableUsersUseCase(
    private val firestoreRepository: FirestoreRepository
) {
    suspend operator fun invoke(): List<UserFirestore> {
        return try {
            firestoreRepository.getAllUsers()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

