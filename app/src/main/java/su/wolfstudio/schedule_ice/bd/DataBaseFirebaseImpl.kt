package su.wolfstudio.schedule_ice.bd

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.JsonElement
import su.wolfstudio.schedule_ice.cashe.ApplicationCacheImpl
import su.wolfstudio.schedule_ice.cashe.update
import su.wolfstudio.schedule_ice.model.ListPalaces
import su.wolfstudio.schedule_ice.model.Palaces

class DataBaseFirebaseImpl(applicationCacheImpl: ApplicationCacheImpl) : DataBaseFirebase {

    init {
        val myRef = Firebase.database.reference

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.getValue(ListPalaces::class.java)?.let { palaces ->
                    applicationCacheImpl.listPalaces.update { palaces.items }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    override fun getListPalaces(): List<Palaces> {
        return listOf(
            Palaces(
                id = 0,
                name = "Солнцево",
                url = "https://arena-solntsevo.ru/",
                urlSchedule = "https://arena-solntsevo.ru/raspisanie",
                urlRoute = "https://yandex.ru/maps/org/ledovaya_arena_solntsevo/68421173641"
            ),
            Palaces(
                id = 1,
                name = "Солнцево2",
                url = "https://arena-solntsevo.ru/",
                urlSchedule = "https://arena-solntsevo.ru/raspisanie",
                urlRoute = "https://yandex.ru/maps/-/CDsOumS"
            ),
        )
    }
}