package org.christophertwo.daelia.social.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.christophertwo.daelia.auth.api.GoogleAuthManager
import org.christophertwo.daelia.auth.impl.firebase.GoogleAuthManagerImpl
import org.christophertwo.daelia.profile.api.FirestoreRepository
import org.christophertwo.daelia.profile.impl.firestore.FirestoreRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val DataModule: Module
    get() = module {
        singleOf(::GoogleAuthManagerImpl).bind(GoogleAuthManager::class)
        singleOf(::FirestoreRepositoryImpl).bind(FirestoreRepository::class)

        single<FirebaseAuth> { FirebaseAuth.getInstance() }
        single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
    }