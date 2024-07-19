package com.adinda.gotrash.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adinda.gotrash.data.repository.auth.AuthRepository

class HomeViewModel(private val repository: AuthRepository) : ViewModel() {
    fun getCurrentUser() = repository.getCurrentUser()
}