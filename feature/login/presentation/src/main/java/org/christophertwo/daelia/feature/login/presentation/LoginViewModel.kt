package org.christophertwo.daelia.feature.login.presentation

import android.content.Context
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.daelia.feature.login.domain.SignInWithGoogleUseCase

class LoginViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val context: Context
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(context, R.string.web_id))
                        .requestEmail()
                        .build()
                val googleSignInClient = GoogleSignIn.getClient(context, gso)
                _state.update {
                    it.copy(
                        googleSignInClient = googleSignInClient
                    )
                }
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.ContinueWhitGoogle -> {
                viewModelScope.launch {
                    signInWithGoogleUseCase.invoke(
                        result = action.result,
                        onAuthComplete = {
                            _state.update { it.copy(isLoggedIn = true) }
                        },
                        onAuthError = {
                            _state.update { it.copy(error = it.error, isLoggedIn = false) }
                        }
                    )
                }
            }
        }
    }

}