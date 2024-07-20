package com.adinda.gotrash.data.datasource.trash

import androidx.lifecycle.LiveData
import com.adinda.gotrash.data.model.Trash
import com.adinda.gotrash.data.source.firebase.RealTimeDatabaseService

interface TrashDataSource {
    fun getTrashLiveData(): LiveData<Trash>
}

class TrashDataSourceImpl(private val service: RealTimeDatabaseService) : TrashDataSource {
    override fun getTrashLiveData(): LiveData<Trash> {
        return service.trashLiveData
    }
}