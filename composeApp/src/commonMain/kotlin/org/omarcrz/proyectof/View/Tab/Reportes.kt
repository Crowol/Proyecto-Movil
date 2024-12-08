package org.omarcrz.proyectof.View.Tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.omarcrz.proyectof.View.Screen.ReportesView

object Reportes : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Filled.CheckCircle)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Reportes",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        ReportesView()
    }
}
