package com.a65apps.weather.di.app

import android.content.Context
import com.a65apps.weather.App
import com.a65apps.weather.di.ActivityBindingModule
import com.a65apps.weather.di.core.DataModule
import com.a65apps.weather.di.core.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityBindingModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        NavigationModule::class,
        DataModule::class,
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context): Builder

        fun build(): AppComponent
    }
}
