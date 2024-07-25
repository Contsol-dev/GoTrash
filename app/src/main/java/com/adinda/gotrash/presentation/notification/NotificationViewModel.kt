package com.adinda.gotrash.presentation.notification

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adinda.gotrash.data.model.Notification
import com.adinda.gotrash.data.repository.notification.NotificationRepository
import com.adinda.gotrash.utils.ResultWrapper
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _notifications = MutableLiveData<ResultWrapper<List<Notification>>>()
    val notifications: LiveData<ResultWrapper<List<Notification>>> get() = _notifications

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            notificationRepository.getNotifications()
                .collect { result ->
                    _notifications.value = result
                }
        }
    }

    fun markAsRead(notificationId: Int) {
        viewModelScope.launch {
            when (val result = notificationRepository.markAsRead(notificationId)) {
                is ResultWrapper.Success -> {
                    val updatedNotifications = _notifications.value?.payload?.map {
                        if (it.id == notificationId) it.copy(isRead = 1) else it
                    }
                    _notifications.value = ResultWrapper.Success(updatedNotifications ?: emptyList())
                }

                else -> {
                    Log.e("ErrorNotification", "Error Gatau")
                }
            }
        }
    }
}