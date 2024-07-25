package com.adinda.gotrash.data.repository.notification

import com.adinda.gotrash.data.datasource.notification.NotificationDataSource
import com.adinda.gotrash.data.model.Notification
import com.adinda.gotrash.utils.ResultWrapper
import com.adinda.gotrash.utils.proceed
import com.adinda.gotrash.utils.proceedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

interface NotificationRepository {
    fun getNotifications(): Flow<ResultWrapper<List<Notification>>>
    suspend fun markAsRead(id: Int): ResultWrapper<Unit>
}

class NotificationRepositoryImpl(private val dataSource: NotificationDataSource) :
    NotificationRepository {
    override fun getNotifications(): Flow<ResultWrapper<List<Notification>>> {
        return proceedFlow {
            dataSource.getNotifications()
        }.catch {
            emit(ResultWrapper.Error(Exception(it)))
        }
    }

    override suspend fun markAsRead(id: Int): ResultWrapper<Unit> {
        return proceed {
            dataSource.markAsRead(id)
        }
    }
}