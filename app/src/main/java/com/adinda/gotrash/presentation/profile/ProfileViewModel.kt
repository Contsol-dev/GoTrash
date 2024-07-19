package com.adinda.gotrash.presentation.profile

import androidx.lifecycle.ViewModel
import com.adinda.gotrash.data.repository.auth.AuthRepository

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {
    fun doLogout() = repository.doLogout()
}