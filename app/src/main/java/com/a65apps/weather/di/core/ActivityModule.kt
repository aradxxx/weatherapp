package com.a65apps.weather.di.core

import com.a65apps.weather.presentation.core.BaseActivity
import com.a65apps.weather.presentation.core.State
import dagger.Module
import dagger.Provides

@Module
abstract class ActivityModule<A : BaseActivity<*, S>, S : State> {
    @Provides
    fun provideRestoredState(activity: A) = activity.restoredState
}
