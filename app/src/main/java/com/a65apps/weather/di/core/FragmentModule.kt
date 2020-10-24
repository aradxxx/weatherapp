package com.a65apps.weather.di.core

import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.core.State
import com.a65apps.weather.presentation.core.navigation.AppRouter
import dagger.Module
import dagger.Provides
import ru.aradxxx.ciceronetabs.NavigationContainer

@Module
abstract class FragmentModule<F : BaseFragment<*, S>, S : State> {
    @Provides
    fun provideRestoredState(fragment: F): S? {
        return fragment.restoredState
    }

    @Provides
    fun provideRouter(fragment: F): AppRouter {
        val parentFragment = fragment.getParentFragment()
        if (parentFragment is NavigationContainer<*>) {
            val router = parentFragment.router()
            if (router is AppRouter) return router
        }

        val activity = fragment.requireActivity()
        if (activity is NavigationContainer<*>) {
            val router = activity.router()
            if (router is AppRouter) return router
        }
        throw IllegalStateException("Can't provide router for " + fragment::class.simpleName)
    }
}
