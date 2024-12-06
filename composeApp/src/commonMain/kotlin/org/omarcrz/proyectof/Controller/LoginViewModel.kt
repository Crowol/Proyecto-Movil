package org.omarcrz.proyectof.Controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun onEmailChange(newEmail: String) {
        viewModelScope.launch {
            _email.value = newEmail
        }
    }

    fun onPasswordChange(newPassword: String) {
        viewModelScope.launch {
            _password.value = newPassword
        }
    }

}
