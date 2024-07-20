package com.adinda.gotrash.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adinda.gotrash.data.local.model.Notification

@Dao
interface NotificationDao {
    @Insert
    suspend fun insert(notification: Notification)

    @Query("SELECT * FROM notifications")
    fun getAllNotifications(): LiveData<List<Notification>>
}
