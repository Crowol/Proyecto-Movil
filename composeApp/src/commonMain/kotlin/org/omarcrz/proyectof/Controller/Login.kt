package org.omarcrz.proyectof.Controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.omarcrz.proyectof.View.Screen.LoginView


class Login : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        fun onLoginClick() {
            if (validateCredentials(email, password)) {
                navigator?.push(Nav())
            } else {
                errorMessage = "Credenciales inválidas"
            }
        }

        LoginView(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            onLoginClick = { onLoginClick() },
            errorMessage = errorMessage
        )
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        // Lógica para validar las credenciales (Aqui son las buenas xd)
        return email == "Admin" && password == "Admin"
    }
}
