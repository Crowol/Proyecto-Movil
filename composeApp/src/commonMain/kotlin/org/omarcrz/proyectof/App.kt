package org.omarcrz.proyectof

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.omarcrz.proyectof.Controller.Login


@Composable
fun App() {
    MaterialTheme {
        Navigator(screen = Login()) { navigator ->
            SlideTransition(navigator)
        }
    }
}


