package com.adinda.gotrash.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adinda.gotrash.data.model.Trash
import com.adinda.gotrash.data.repository.auth.AuthRepository
import com.adinda.gotrash.data.repository.trash.TrashRepository

class HomeViewModel(
    private val repository: AuthRepository,
    private val trashRepository: TrashRepository
) : ViewModel() {
    fun getCurrentUser() = repository.getCurrentUser()
    val trash: LiveData<Trash> = trashRepository.getTrashLiveData()
}