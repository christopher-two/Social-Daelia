package org.christophertwo.daelia.feature.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.daelia.feature.home.domain.GetAvailableUsersUseCase
import org.christophertwo.daelia.feature.home.domain.GetFriendsUseCase
import org.christophertwo.daelia.feature.home.domain.GetUserUseCase

class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getAvailableUsersUseCase: GetAvailableUsersUseCase,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadUserData()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

    private fun loadUserData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                // Obtener el usuario principal
                val user = getUserUseCase.invoke()
                Log.d("HomeViewModel", "User: $user")

                // Obtener los amigos desde Firestore
                val friends = getFriendsUseCase.invoke()
                Log.d("HomeViewModel", "Friends from Firestore: ${friends.size}")

                if (friends.isEmpty()) {
                    // Si no tiene amigos, obtener todos los usuarios disponibles
                    Log.d("HomeViewModel", "No friends found, loading available users")
                    val availableUsers = getAvailableUsersUseCase.invoke()
                    Log.d("HomeViewModel", "Available users: ${availableUsers.size}")

                    // Si tampoco hay usuarios en Firestore, usar datos dummy
                    val finalUsers = availableUsers.ifEmpty {
                        Log.d("HomeViewModel", "No available users, using dummy data")
                        getDummyFriends()
                    }

                    _state.update {
                        it.copy(
                            user = user,
                            friends = emptyList(),
                            availableUsers = finalUsers,
                            isLoading = false
                        )
                    }
                } else {
                    // Si tiene amigos, mostrar la red con conexiones
                    _state.update {
                        it.copy(
                            user = user,
                            friends = friends,
                            availableUsers = emptyList(),
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error loading user data", e)
                // En caso de error, usar datos dummy
                _state.update {
                    it.copy(
                        friends = emptyList(),
                        availableUsers = getDummyFriends(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onAction(action: HomeAction) {
        // Manejar acciones futuras aquí
    }

}