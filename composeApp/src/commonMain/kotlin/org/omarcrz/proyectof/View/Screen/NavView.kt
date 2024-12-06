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
            BottomNavigation {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        selected = currentTab.key == tab.key,
                        label = { Text(tab.options.title) },
                        icon = {
                            tab.options.icon?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = null
                                )
                            }
                        },
                        onClick = { onTabSelected(tab) }
                    )
                }
            }
        }
    ) {
        CurrentTab()
    }
}

