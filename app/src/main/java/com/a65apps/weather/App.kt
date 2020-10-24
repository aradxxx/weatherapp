package com.a65apps.weather

import com.a65apps.weather.di.app.AppComponent
import com.a65apps.weather.di.app.DaggerAppComponent
import com.a65apps.weather.presentation.util.DebugTree
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree("DDD"))
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component: AppComponent = DaggerAppComponent.builder()
            .bindContext(this)
            .build()
        component.inject(this)
        return component
    }
}
