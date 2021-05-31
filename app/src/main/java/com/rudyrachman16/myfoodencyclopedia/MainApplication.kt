package com.rudyrachman16.myfoodencyclopedia

import android.app.Application
import com.rudyrachman16.core.di.apiModule
import com.rudyrachman16.core.di.dbModule
import com.rudyrachman16.core.di.repositoriesModule
import com.rudyrachman16.myfoodencyclopedia.di.useCaseModule
import com.rudyrachman16.myfoodencyclopedia.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(dbModule, apiModule, repositoriesModule, useCaseModule, viewModelModule)
            )
        }
    }
}