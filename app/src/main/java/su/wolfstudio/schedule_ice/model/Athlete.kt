package su.wolfstudio.schedule_ice.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "athlete")
data class Athlete(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val surname: String,
    val middleName: String? = null,
    val birthday: Long,
    val isDelete: Boolean = false
){
    companion object{
        fun getAthlete() = Athlete(name = "", surname = "", middleName = "", birthday = 0L)
    }
}