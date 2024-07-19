package com.adinda.gotrash.presentation.main

import androidx.lifecycle.ViewModel
import com.adinda.gotrash.data.repository.auth.AuthRepository

class MainViewModel(private val repository: AuthRepository) : ViewModel() {
    fun isLogin() = repository.isLoggedIn()
}