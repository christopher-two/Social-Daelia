package org.christophertwo.daelia.social.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.christophertwo.daelia.core.common.DiQualifiers
import org.christophertwo.daelia.session.api.SessionRepository
import org.christophertwo.daelia.session.impl.datastore.DataStoreSessionRepository
import org.koin.core.module.Module
import org.koin.dsl.module

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session_prefs")
private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")

val SessionModule: Module
    get() = module {
        single<SessionRepository> {
            DataStoreSessionRepository(get(qualifier = DiQualifiers.SESSION_DATASTORE))
        }
        single(qualifier = DiQualifiers.SESSION_DATASTORE) {
            get<Context>().sessionDataStore
        }
    }