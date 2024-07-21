package com.adinda.gotrash.data.source.firebase.network.dto

data class SendMessagesDto (
    val to: String?,
    val notification: NotificationBody?
)

data class NotificationBody(
    val title: String?,
    val body: String?
)
