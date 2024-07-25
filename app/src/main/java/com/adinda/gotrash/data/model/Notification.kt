package com.adinda.gotrash.data.model

data class Notification(
    val id: Int,
    val volume: String,
    val sentAt: String,
    var isRead: Boolean
)