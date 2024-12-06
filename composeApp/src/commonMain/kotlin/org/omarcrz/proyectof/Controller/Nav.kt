package org.omarcrz.proyectof.Controller

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import org.omarcrz.proyectof.View.Screen.NavView
import org.omarcrz.proyectof.View.Tab.Inventario
import org.omarcrz.proyectof.View.Tab.Reportes
import org.omarcrz.proyectof.View.Tab.Usuarios
import org.omarcrz.proyectof.View.Tab.Ventas

class Nav : Screen {
    @Composable
    override fun Content() {
        TabNavigator(
            Usuarios,
            tabDisposable = {
                TabDisposable(
                    it,
                    listOf(Usuarios, Reportes, Ventas, Inventario)
                )
            }
        ) {
            val tabNavigator = LocalTabNavigator.current
            val tabs = listOf(Usuarios, Reportes, Ventas, Inventario)

            NavView(
                currentTab = tabNavigator.current,
                onTabSelected = { tabNavigator.current = it },
                tabs = tabs
            )
        }
    }
}
