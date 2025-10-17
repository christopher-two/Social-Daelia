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
import org.christophertwo.daelia.feature.home.domain.UpdateFriendsUseCase

class HomeViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getFriendsUseCase: GetFriendsUseCase,
    private val getAvailableUsersUseCase: GetAvailableUsersUseCase,
    private val updateFriendsUseCase: UpdateFriendsUseCase,
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

                // Obtener todos los usuarios disponibles
                Log.d("HomeViewModel", "Loading available users")
                val allUsers = getAvailableUsersUseCase.invoke()
                Log.d("HomeViewModel", "All users: ${allUsers.size}")

                // Si no hay usuarios en Firestore, usar datos dummy
                val usersToShow = allUsers.ifEmpty {
                    Log.d("HomeViewModel", "No users in Firestore, using dummy data")
                    getDummyFriends()
                }

                // Filtrar usuarios que ya son amigos
                val availableUsers = usersToShow.filter { availableUser ->
                    friends.none { friend ->
                        friend.name == availableUser.name && friend.imageUrl == availableUser.imageUrl
                    }
                }

                _state.update {
                    it.copy(
                        user = user,
                        friends = friends,
                        availableUsers = availableUsers,
                        isLoading = false
                    )
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
        when (action) {
            is HomeAction.OnUserClick -> {
                // Verificar si el usuario ya es amigo
                val isAlreadyFriend = _state.value.friends.any { friend ->
                    friend.name == action.user.name && friend.imageUrl == action.user.imageUrl
                }

                // Solo mostrar diálogo si NO es amigo
                if (!isAlreadyFriend) {
                    _state.update { it.copy(selectedUserForDialog = action.user) }
                } else {
                    Log.d("HomeViewModel", "User is already a friend: ${action.user.name}")
                }
            }
            is HomeAction.AddFriend -> {
                // Agregar amigo
                viewModelScope.launch {
                    try {
                        val currentFriends = _state.value.friends.toMutableList()

                        // Verificar nuevamente que no sea amigo (por si acaso)
                        val isAlreadyFriend = currentFriends.any { friend ->
                            friend.name == action.user.name && friend.imageUrl == action.user.imageUrl
                        }

                        if (isAlreadyFriend) {
                            Log.d("HomeViewModel", "User is already a friend, skipping")
                            _state.update { it.copy(selectedUserForDialog = null) }
                            return@launch
                        }

                        // Agregar el nuevo amigo a la lista
                        currentFriends.add(action.user)

                        // Actualizar en Firestore
                        updateFriendsUseCase.invoke(currentFriends)

                        Log.d("HomeViewModel", "Friend added: ${action.user.name}")

                        // Actualizar el estado local
                        _state.update {
                            it.copy(
                                friends = currentFriends,
                                availableUsers = it.availableUsers.filter { user ->
                                    user.name != action.user.name || user.imageUrl != action.user.imageUrl
                                },
                                selectedUserForDialog = null
                            )
                        }
                    } catch (e: Exception) {
                        Log.e("HomeViewModel", "Error adding friend", e)
                        _state.update { it.copy(selectedUserForDialog = null) }
                    }
                }
            }
            is HomeAction.DismissDialog -> {
                _state.update { it.copy(selectedUserForDialog = null) }
            }
        }
    }

}