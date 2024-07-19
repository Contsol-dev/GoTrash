package com.adinda.gotrash.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.adinda.gotrash.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers

class SignupViewModel(private val repository: AuthRepository) : ViewModel() {
    fun doRegister(
        fullName: String,
        email: String,
        password: String,
    ) = repository.doRegister(
        fullName = fullName,
        email = email,
        password = password,
    ).asLiveData(Dispatchers.IO)
}