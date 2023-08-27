package su.wolfstudio.schedule_ice.bd

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import su.wolfstudio.schedule_ice.cashe.ApplicationCacheImpl
import su.wolfstudio.schedule_ice.cashe.update
import su.wolfstudio.schedule_ice.model.ListPalaces

class DataBaseFirebaseImpl(applicationCacheImpl: ApplicationCacheImpl) : DataBaseFirebase {

    init {
        val myRef = Firebase.database.reference

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.getValue(ListPalaces::class.java)?.let { palaces ->
                    applicationCacheImpl.listPalace.update { palaces.items }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }
}