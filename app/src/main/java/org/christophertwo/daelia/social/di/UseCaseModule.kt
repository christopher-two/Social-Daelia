package org.christophertwo.daelia.social.di

import org.christophertwo.daelia.feature.login.domain.SignInWithGoogleUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule: Module
    get() = module {
        singleOf(::SignInWithGoogleUseCase)
    }