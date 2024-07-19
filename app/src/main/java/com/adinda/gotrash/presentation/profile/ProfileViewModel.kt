package com.adinda.gotrash.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.adinda.gotrash.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    fun doLogout() = repository.doLogout().asLiveData(Dispatchers.IO)
    fun getUser() = repository.getCurrentUser()
    fun isLogin() = repository.isLoggedIn()
}