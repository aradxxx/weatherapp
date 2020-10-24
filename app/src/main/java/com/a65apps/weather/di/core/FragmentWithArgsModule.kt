package com.a65apps.weather.di.core

import android.os.Parcelable
import com.a65apps.weather.presentation.core.BaseFragment
import com.a65apps.weather.presentation.core.State
import dagger.Module
import dagger.Provides

@Module
abstract class FragmentWithArgsModule<F : BaseFragment<*, S>, S : State, A : Parcelable> :
    FragmentModule<F, S>() {

    @Provides
    fun provideInitialArgs(fragment: F): A = fragment.initialArguments()
}
