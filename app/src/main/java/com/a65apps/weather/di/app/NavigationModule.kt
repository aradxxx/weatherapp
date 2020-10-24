package com.a65apps.weather.di.app

import com.a65apps.weather.presentation.core.navigation.AppRouter
import dagger.Module
import dagger.Provides
import ru.aradxxx.ciceronetabs.TabCicerone
import ru.aradxxx.ciceronetabs.TabRouterFactory
import javax.inject.Singleton

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideRouterFactory(): TabRouterFactory<AppRouter> = TabRouterFactory {
        AppRouter()
    }

    @Provides
    @Singleton
    fun provideCiceroneTab(tabRouterFactory: TabRouterFactory<AppRouter>) =
        TabCicerone(tabRouterFactory)
}
