package com.a65apps.weather.presentation.core

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.a65apps.weather.di.core.ViewModelFactory
import com.a65apps.weather.presentation.util.Const
import com.a65apps.weather.presentation.util.toast
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import java.io.Serializable
import javax.inject.Inject

private const val BUNDLE_VIEW_STATE = "VIEW_STATE"

@SuppressWarnings("TooManyFunctions")
abstract class BaseFragment<VM : BaseViewModel<S>, S : State>(
    layoutRes: Int
) : Fragment(layoutRes) {
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

    protected open fun onEvent(event: Event) {
        when (event) {
            is MessageEvent -> {
                if (event.message.isNotEmpty()) {
                    requireContext().toast(event.message)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        restoredState = savedInstanceState?.getSerializable(BUNDLE_VIEW_STATE) as S?
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
        viewModel.stateLiveData().observe(
            viewLifecycleOwner,
            {
                Timber.d("new state: %s", it.toString())
                updateState(it)
            }
        )
        viewModel.eventLiveData().observe(viewLifecycleOwner, { onEvent(it) })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(BUNDLE_VIEW_STATE, viewModel.stateLiveData().value)
        super.onSaveInstanceState(outState)
    }

    @Suppress("UNCHECKED_CAST")
    fun <A : Serializable> initialArguments(): A {
        arguments?.getSerializable(Const.BUNDLE_INITIAL_ARGS)?.also { return it as A }
        throw IllegalArgumentException("Fragment doesn't contain initial args")
    }
}
