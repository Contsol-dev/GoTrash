package com.adinda.gotrash.data.datasource.notification

import com.adinda.gotrash.data.mapper.toNotifications
import com.adinda.gotrash.data.model.Notification
import com.adinda.gotrash.data.source.api.service.ApiService

interface NotificationDataSource {
    suspend fun getNotifications(): List<Notification>
    suspend fun markAsRead(id : Int)
}

class NotificationDataSourceImpl(private val service: ApiService): NotificationDataSource {
    override suspend fun getNotifications(): List<Notification> {
        return service.getNotification().toNotifications()
    }

    override suspend fun markAsRead(id: Int) {
        service.markAsRead(id)
    }
}