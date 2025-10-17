package org.christophertwo.daelia.feature.login.domain

import android.util.Log
import androidx.activity.result.ActivityResult
import com.google.android.gms.common.api.ApiException
import org.christophertwo.daelia.auth.api.GoogleAuthManager
import org.christophertwo.daelia.profile.api.FirestoreRepository
import org.christophertwo.daelia.profile.api.UserFirestore
import org.christophertwo.daelia.session.api.SessionRepository

class SignInWithGoogleUseCase(
    private val googleAuthManager: GoogleAuthManager,
    private val sessionRepository: SessionRepository,
    private val firestoreRepository: FirestoreRepository
) {
    companion object {
        private const val TAG = "SignInWithGoogleUseCase"
    }

    suspend operator fun invoke(
        result: ActivityResult,
        onAuthComplete: () -> Unit,
        onAuthError: () -> Unit
    ) {
        val authResultOutcome = googleAuthManager.handleSignInResultWithFirebase(result.data)
        authResultOutcome.fold(
            onSuccess = { authResult ->
                authResult.user?.let { user ->
                    sessionRepository.saveUserSession(user.uid)

                    // Verificar si el usuario ya existe en Firestore
                    val existingUser = try {
                        firestoreRepository.userProfile()
                    } catch (e: Exception) {
                        null
                    }

                    // Si no existe, crear un nuevo perfil
                    if (existingUser == null) {
                        firestoreRepository.updateUserProfile(
                            UserFirestore(
                                name = user.displayName ?: user.email ?: user.uid,
                                imageUrl = user.photoUrl?.toString(), // Usar toString() en lugar de path
                                friends = listOf()
                            )
                        )
                        Log.d(TAG, "New user profile created with image: ${user.photoUrl?.toString()}")
                    } else {
                        Log.d(TAG, "Existing user profile found: $existingUser")
                    }

                    Log.d(TAG, "User signed in: ${user.uid}")
                    onAuthComplete()
                }
            },
            onFailure = { throwable ->
                if (throwable is ApiException) {
                    Log.e(TAG, "Google sign-in failed", throwable)
                    onAuthError()
                } else {
                    Log.e(TAG, "Error during Google sign-in", throwable)
                    onAuthError()
                }
            }
        )
    }
}