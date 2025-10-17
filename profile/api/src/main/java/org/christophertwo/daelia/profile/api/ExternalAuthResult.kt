package org.christophertwo.daelia.profile.api

sealed class ExternalAuthResult {
    data class Success(val idToken: String) : ExternalAuthResult()
    data class Error(val message: String) : ExternalAuthResult()
    object Cancelled : ExternalAuthResult()
}