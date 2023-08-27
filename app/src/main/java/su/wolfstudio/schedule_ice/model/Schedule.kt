package su.wolfstudio.schedule_ice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "schedule")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val palaceId: Long,
    val date: LocalDate,
    val startTime: Int = 600,// В минутах
    val endTime: Int = 660,
    val type: ScheduleType = ScheduleType.DEFAULT,
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