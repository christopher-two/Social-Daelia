package org.christophertwo.daelia.feature.login.presentation

import com.google.android.gms.auth.api.signin.GoogleSignInClient

data class LoginState(
    val googleSignInClient: GoogleSignInClient? = null,
    val isLoggedIn: Boolean = false,
    val error: String? = null
)