package org.christophertwo.daelia.core.common

import org.koin.core.qualifier.named

// Este objeto contiene las "etiquetas" que usaremos en toda la app.
object DiQualifiers {
    val SESSION_DATASTORE = named("SessionDataStore")
    val SETTINGS_DATASTORE = named("SettingsDataStore")
    val USER_PREFERENCES_DATASTORE = named("UserPreferencesDataStore")
}