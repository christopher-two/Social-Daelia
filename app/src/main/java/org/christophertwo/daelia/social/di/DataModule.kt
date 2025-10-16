package org.christophertwo.daelia.social.di

import org.christophertwo.daelia.auth.api.GoogleAuthManager
import org.christophertwo.daelia.auth.impl.firebase.GoogleAuthManagerImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val DataModule: Module
    get() = module {
        singleOf(::GoogleAuthManagerImpl).bind(GoogleAuthManager::class)
    }