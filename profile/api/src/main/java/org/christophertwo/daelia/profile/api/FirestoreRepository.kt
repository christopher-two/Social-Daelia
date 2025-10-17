package org.christophertwo.daelia.profile.api

interface FirestoreRepository {
    suspend fun userProfile(): UserFirestore
    suspend fun updateFriends(friends: List<UserFirestore>)
    suspend fun updateUserProfile(user: UserFirestore)
}