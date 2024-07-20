package com.adinda.gotrash.data.source.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adinda.gotrash.data.model.Trash
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

interface RealTimeDatabaseService {
    val trashLiveData: LiveData<Trash>
}

class RealTimeDatabaseServiceImpl(private val database : DatabaseReference) : RealTimeDatabaseService {
    private val _trashLiveData = MutableLiveData<Trash>()
    override val trashLiveData: LiveData<Trash> get() = _trashLiveData

    init {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val trash = snapshot.getValue(Trash::class.java)
                _trashLiveData.value = trash
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}