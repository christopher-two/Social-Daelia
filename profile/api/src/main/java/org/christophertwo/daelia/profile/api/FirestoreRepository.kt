package org.christophertwo.daelia.profile.api

interface FirestoreRepository {
    suspend fun userProfile(): UserFirestore
    suspend fun getFriends(): List<UserFirestore>
    suspend fun getAllUsers(): List<UserFirestore>
    suspend fun updateFriends(friends: List<UserFirestore>)
    suspend fun updateUserProfile(user: UserFirestore)
}