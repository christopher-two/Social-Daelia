package org.christophertwo.daelia.social.di

import org.christophertwo.daelia.feature.home.domain.GetAvailableUsersUseCase
import org.christophertwo.daelia.feature.home.domain.GetFriendsUseCase
import org.christophertwo.daelia.feature.home.domain.GetUserUseCase
import org.christophertwo.daelia.feature.home.domain.UpdateFriendsUseCase
import org.christophertwo.daelia.feature.home.domain.UpdateUserProfileUseCase
import org.christophertwo.daelia.feature.login.domain.SignInWithGoogleUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule: Module
    get() = module {
        singleOf(::SignInWithGoogleUseCase)
        singleOf(::GetUserUseCase)
        singleOf(::GetFriendsUseCase)
        singleOf(::GetAvailableUsersUseCase)
        singleOf(::UpdateUserProfileUseCase)
        singleOf(::UpdateFriendsUseCase)
    }