package org.christophertwo.daelia.feature.login.presentation

import androidx.activity.result.ActivityResult

sealed interface LoginAction {
    data class ContinueWhitGoogle(val result: ActivityResult) : LoginAction
}