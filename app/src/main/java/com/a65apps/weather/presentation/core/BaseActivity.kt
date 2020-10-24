package com.a65apps.weather.presentation.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.a65apps.weather.di.core.AndroidXInjection
import com.a65apps.weather.di.core.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

private const val BUNDLE_VIEW_STATE = "VIEW_STATE"

abstract class BaseActivity<VM : BaseViewModel<S>, S : State>(
    layoutRes: Int,
    private val vmClass: Class<VM>
) : AppCompatActivity(layoutRes), HasAndroidInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: VM by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(vmClass)
    }
    var restoredState: S? = null

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    protected open fun updateState(state: S) {
        // for implementing
    }

    protected open fun onEvent(event: Any?) {
        // for implementing
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        restoredState = savedInstanceState?.getParcelable(BUNDLE_VIEW_STATE)
        AndroidXInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel.apply {
            stateLiveData().observe(
                this@BaseActivity,
                Observer {
                    Timber.d("new activity state: %s", it.toString())
                    updateState(it)
                }
            )
            eventLiveData().observe(this@BaseActivity, Observer { onEvent(it) })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(BUNDLE_VIEW_STATE, viewModel.stateLiveData().value)
        super.onSaveInstanceState(outState)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}