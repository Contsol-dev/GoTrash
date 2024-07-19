package com.adinda.gotrash.presentation.splash

import androidx.lifecycle.ViewModel
import com.adinda.gotrash.data.repository.auth.AuthRepository

class SplashViewModel(private val repository: AuthRepository) : ViewModel() {
    fun isLogin(): Boolean  = repository.isLoggedIn()
}