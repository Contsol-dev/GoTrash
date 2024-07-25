package com.adinda.gotrash.data.source.api.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NotificationResponseItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("sent_at")
    val sentAt: String?,
    @SerializedName("volume")
    val volume: Int?,
    @SerializedName("is_read")
    val isRead: Int
)