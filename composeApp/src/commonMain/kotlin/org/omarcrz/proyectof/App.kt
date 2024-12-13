package org.omarcrz.proyectof

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.omarcrz.proyectof.Controller.Login
import org.omarcrz.proyectof.Model.Users

@Composable
fun App(){
        Navigator(screen = Login()) { navigator ->
            SlideTransition(navigator)
        }
}


