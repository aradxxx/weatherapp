package com.a65apps.weather.presentation.core.navigation

import com.a65apps.weather.presentation.core.navigation.tab.Tab
import ru.aradxxx.ciceronetabs.TabRouter
import ru.terrakok.cicerone.Screen

class AppRouter : TabRouter() {
    fun backToRoot(tab: Tab) {
        super.backTo(tab.screenKey(), null)
    }

    fun switchTab(tab: Tab) {
        super.switchTab(tab.screen())
    }

    fun backTo(tab: Tab, screen: Screen?) {
        super.backTo(tab.screenKey(), screen)
    }

    fun exit(tab: Tab) {
        super.exit(tab.screenKey())
    }

    fun navigateTo(tab: Tab, screen: Screen) {
        super.navigateTo(tab.screenKey(), screen)
    }

    @SuppressWarnings("SpreadOperator")
    fun newChain(tab: Tab, vararg screens: Screen?) {
        super.newChain(tab.screenKey(), *screens)
    }

    @SuppressWarnings("SpreadOperator")
    fun newRootChain(tab: Tab, vararg screens: Screen?) {
        super.newRootChain(tab.screenKey(), *screens)
    }

    fun newRootScreen(tab: Tab, screen: Screen) {
        super.newRootScreen(tab.screenKey(), screen)
    }

    fun replaceScreen(tab: Tab, screen: Screen) {
        super.replaceScreen(tab.screenKey(), screen)
    }
}
