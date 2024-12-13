package org.omarcrz.proyectof.View.Screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab


@Composable
fun NavView(
    currentTab: Tab,
    onTabSelected: (Tab) -> Unit,
    tabs: List<Tab>
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = RedCatalunya, // Fondo rojo
                contentColor = BackgroundWhite // Texto e íconos blancos
            ) {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        selected = currentTab.key == tab.key,
                        label = {
                            Text(
                                text = tab.options.title,
                                color = if (currentTab.key == tab.key) YellowGold else BackgroundWhite // Amarillo para el seleccionado
                            )
                        },
                        icon = {
                            tab.options.icon?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = null,
                                    tint = if (currentTab.key == tab.key) YellowGold else BackgroundWhite // Ícono amarillo si está seleccionado
                                )
                            }
                        },
                        selectedContentColor = YellowGold, // Color de ícono seleccionado
                        unselectedContentColor = BackgroundWhite, // Color de ícono no seleccionado
                        onClick = { onTabSelected(tab) }
                    )
                }
            }
        }
    ) {
        CurrentTab()
    }
}
