package com.a65apps.weather.presentation.core

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.a65apps.weather.di.core.AndroidXInjection
import com.a65apps.weather.di.core.ViewModelFactory
import com.a65apps.weather.presentation.util.Const
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

private const val BUNDLE_VIEW_STATE = "VIEW_STATE"

@SuppressWarnings("TooManyFunctions")
abstract class BaseFragment<VM : BaseViewModel<S>, S : State>(
    layoutRes: Int
) : Fragment(layoutRes), HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val backPressedDispatcher: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.backPressed()
            }
        }
    protected abstract val vmClass: Class<VM>
    protected val viewModel: VM by lazy {
        ViewModelProvider(this, viewModelFactory).get(vmClass)
    }
    var restoredState: S? = null

    protected open fun updateState(state: S) {
        // for implementing
    }

    protected open fun onEvent(event: Any?) {
        // for implementing
    }

    override fun onAttach(context: Context) {
        AndroidXInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        restoredState = savedInstanceState?.getParcelable(BUNDLE_VIEW_STATE)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        requireActivity().onBackPressedDispatcher.addCallback(backPressedDispatcher)
        super.onResume()
    }

    override fun onPause() {
        backPressedDispatcher.remove()
        super.onPause()
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            stateLiveData().observe(
                viewLifecycleOwner,
                {
                    Timber.d("new state: %s", it.toString())
                    updateState(it)
                }
            )
            eventLiveData().observe(viewLifecycleOwner, Observer { onEvent(it) })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(BUNDLE_VIEW_STATE, viewModel.stateLiveData().value)
        super.onSaveInstanceState(outState)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    fun <A : Parcelable> initialArguments(): A {
        arguments?.getParcelable<A>(Const.BUNDLE_INITIAL_ARGS)?.also { return it }
        throw IllegalArgumentException("Fragment doesn't contain initial args")
    }
}
