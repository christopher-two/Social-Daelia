package org.christophertwo.daelia.feature.home.presentation

import org.christophertwo.daelia.profile.api.UserFirestore

sealed interface HomeAction {
    data class OnUserClick(val user: UserFirestore) : HomeAction
    data class AddFriend(val user: UserFirestore) : HomeAction
    data object DismissDialog : HomeAction
}