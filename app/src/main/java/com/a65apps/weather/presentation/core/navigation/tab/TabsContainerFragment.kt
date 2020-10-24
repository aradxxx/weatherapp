package com.a65apps.weather.presentation.core.navigation.tab

import android.content.Context
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.a65apps.weather.R
import com.a65apps.weather.databinding.FragmentTabsContainerBinding
import com.a65apps.weather.di.core.AndroidXInjection
import com.a65apps.weather.presentation.core.navigation.AppRouter
import com.a65apps.weather.presentation.util.tabNavigator
import com.a65apps.weather.presentation.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.aradxxx.ciceronetabs.TabCicerone
import ru.aradxxx.ciceronetabs.TabNavigator
import javax.inject.Inject

class TabsContainerFragment :
    Fragment(R.layout.fragment_tabs_container),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemReselectedListener,
    TabListener {
    @Inject
    lateinit var tabCicerone: TabCicerone<AppRouter>
    private val navigator: TabNavigator<AppRouter> by lazy {
        tabNavigator(tabCicerone, R.id.tabsContainer)
    }
    private val binding by viewBinding(FragmentTabsContainerBinding::bind)

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        bnwSilently { router().switchTab(Tab.from(menuItem.itemId).screen()) }
        return true
    }

    override fun onNavigationItemReselected(menuItem: MenuItem) {
        router().backToRoot(Tab.from(menuItem.itemId))
    }

    override fun tabChanged(tag: Int) {
        bnwSilently { binding.bottomNavigationView.selectedItemId = tag }
    }

    override fun onAttach(context: Context) {
        AndroidXInjection.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        tabCicerone.tabsContainerCicerone().navigatorHolder.setNavigator(navigator)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener(this)
    }

    override fun onPause() {
        tabCicerone.tabsContainerCicerone().navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun router(): AppRouter {
        return tabCicerone.tabsContainerRouter()
    }

    private fun bnwSilently(action: () -> Unit) {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(null)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener(null)
        action()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this)
        binding.bottomNavigationView.setOnNavigationItemReselectedListener(this)
    }
}
