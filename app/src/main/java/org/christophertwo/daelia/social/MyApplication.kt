package org.christophertwo.daelia.social

import android.app.Application
import org.christophertwo.daelia.social.di.DataModule
import org.christophertwo.daelia.social.di.SessionModule
import org.christophertwo.daelia.social.di.UseCaseModule
import org.christophertwo.daelia.social.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            printLogger(Level.ERROR)
            modules(
                ViewModelModule,
                SessionModule,
                UseCaseModule,
                DataModule
            )
        }
    }
}