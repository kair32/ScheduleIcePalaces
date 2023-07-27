package su.wolfstudio.schedule_ice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "schedule")
data class Schedule(
    @PrimaryKey
    val id: Int,
    val palaceId: Long,
    val date: LocalDate,
    var startTime: Int = 600,// В минутах
    var endTime: Int = 660,
) {
    fun getStartTimeHour() = startTime / 60
    fun getStartTimeMin() = startTime % 60
    fun getEndTimeHour() = endTime / 60
    fun getEndTimeMin() = endTime % 60

    companion object{
        fun getTimeWithZero(hour: Int, min: Int): String {
            return "${if (hour < 10) "0" else ""}$hour:${if (min < 10) "0" else ""}$min"
        }
    }
}