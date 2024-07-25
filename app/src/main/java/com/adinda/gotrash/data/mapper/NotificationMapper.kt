package com.adinda.gotrash.data.mapper

import com.adinda.gotrash.data.model.Notification
import com.adinda.gotrash.data.source.api.model.NotificationResponseItem

fun NotificationResponseItem.toNotification() =
    Notification(
        id = this.id ?: 0,
        volume = this.volume.toString(),
        sentAt = this.sentAt.toString(),
        isRead = this.isRead
    )


fun List<NotificationResponseItem>.toNotifications(): List<Notification> {
    return this.map { it.toNotification() }
}