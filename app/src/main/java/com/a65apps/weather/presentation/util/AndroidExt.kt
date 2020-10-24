package com.a65apps.weather.presentation.util

import android.content.Context
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.a65apps.weather.presentation.core.FragmentViewBindingDelegate
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.main.MainActivity
import ru.aradxxx.ciceronetabs.TabCicerone
import ru.aradxxx.ciceronetabs.TabNavigator

fun Fragment.tabNavigator(
    tabCicerone: TabCicerone<AppRouter>,
    container: Int
): TabNavigator<AppRouter> {
    val activity: MainActivity = requireActivity() as MainActivity
    return TabNavigator(activity, tabCicerone, childFragmentManager, container)
}

fun <F : Fragment> F.withInitialArguments(params: Parcelable) = apply {
    arguments = bundleOf(Const.BUNDLE_INITIAL_ARGS to params)
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this, message, duration).apply { show() }
