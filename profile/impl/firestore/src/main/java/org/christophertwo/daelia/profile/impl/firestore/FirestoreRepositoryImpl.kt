package org.christophertwo.daelia.profile.impl.firestore

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.christophertwo.daelia.profile.api.FirestoreRepository
import org.christophertwo.daelia.profile.api.UserFirestore

class FirestoreRepositoryImpl(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : FirestoreRepository {
    override suspend fun userProfile(): UserFirestore {
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        val document = db.collection("users").document(uid).get().await()

        return document.toObject(UserFirestore::class.java) ?: throw IllegalStateException("User not found")
    }

    override suspend fun updateFriends(friends: List<UserFirestore>) {
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        db.collection("users").document(uid).update("friends", friends).await()
    }

    override suspend fun updateUserProfile(user: UserFirestore) {
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")
        db.collection("users").document(uid).set(user).await()
    }
}