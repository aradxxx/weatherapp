package com.a65apps.weather.presentation.core.navigation.tab

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.a65apps.weather.R
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.util.Const
import com.a65apps.weather.presentation.util.tabNavigator
import dagger.android.support.AndroidSupportInjection
import ru.aradxxx.ciceronetabs.NavigationContainer
import ru.aradxxx.ciceronetabs.TabCicerone
import ru.aradxxx.ciceronetabs.TabNavigator
import ru.aradxxx.ciceronetabs.TabRouter
import ru.terrakok.cicerone.Cicerone
import javax.inject.Inject

class TabFragment : Fragment(R.layout.fragment_tab), NavigationContainer<TabRouter> {
    @Inject
    lateinit var tabCicerone: TabCicerone<AppRouter>
    private val navigator: TabNavigator<AppRouter> by lazy {
        tabNavigator(tabCicerone, R.id.tab_container)
    }
    private var tabTag: Int = 0
    private var tabListener: TabListener? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (parentFragment is TabListener) {
            tabListener = parentFragment as TabListener
        }
    }

    companion object {
        fun newInstance(tag: Int) = TabFragment().apply {
            arguments = bundleOf(Const.BUNDLE_INITIAL_ARGS to tag)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabTag = requireArguments().getInt(Const.BUNDLE_INITIAL_ARGS)
    }

    override fun onResume() {
        super.onResume()
        cicerone().navigatorHolder.setNavigator(navigator)
        tabListener?.apply {
            tabChanged(tabTag)
        }
    }

    override fun onPause() {
        cicerone().navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun cicerone(): Cicerone<AppRouter> {
        return tabCicerone.cicerone(tabTag.toString())
    }

    override fun router(): TabRouter = cicerone().router
}
