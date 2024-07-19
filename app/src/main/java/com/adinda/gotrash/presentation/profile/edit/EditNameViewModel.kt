package com.adinda.gotrash.presentation.profile.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.adinda.gotrash.data.repository.auth.AuthRepository
import kotlinx.coroutines.Dispatchers

class EditNameViewModel(private val repository: AuthRepository) : ViewModel() {
    fun getUser() = repository.getCurrentUser()
    fun updateName(name: String) = repository.updateProfile(name).asLiveData(Dispatchers.IO)
}