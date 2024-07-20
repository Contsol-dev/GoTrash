package com.adinda.gotrash.data.repository.trash

import androidx.lifecycle.LiveData
import com.adinda.gotrash.data.datasource.trash.TrashDataSource
import com.adinda.gotrash.data.model.Trash

interface TrashRepository {
    fun getTrashLiveData(): LiveData<Trash>
}

class TrashRepositoryImpl(private val dataSource: TrashDataSource) : TrashRepository {
    override fun getTrashLiveData(): LiveData<Trash> {
        return dataSource.getTrashLiveData()
    }
}