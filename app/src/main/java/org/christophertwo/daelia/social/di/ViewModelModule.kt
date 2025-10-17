package org.christophertwo.daelia.social.di

import org.christophertwo.daelia.feature.home.presentation.HomeViewModel
import org.christophertwo.daelia.feature.login.presentation.LoginViewModel
import org.christophertwo.daelia.social.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelModule: Module
    get() = module {
        viewModelOf(::LoginViewModel)
        viewModelOf(::SplashViewModel)
        viewModelOf(::HomeViewModel)
    }