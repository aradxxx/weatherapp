package com.a65apps.weather.di.core

import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.core.State
import dagger.Module
import dagger.Provides
import java.io.Serializable

@Module
abstract class FragmentWithArgsModule<F : BaseFragment<*, S>, S : State, A : Serializable> :
    FragmentModule<F, S>() {

    @Provides
    fun provideInitialArgs(fragment: F): A = fragment.initialArguments()
}
