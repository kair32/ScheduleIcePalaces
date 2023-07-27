package su.wolfstudio.schedule_ice.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import su.wolfstudio.schedule_ice.model.Schedule

@Dao
interface ScheduleDao {

    @Query("SELECT * from schedule WHERE palaceId=:palaceId")
    fun getScheduleByPalaceIdStream(palaceId: Long): Flow<List<Schedule>>

    @Query("SELECT * from schedule WHERE palaceId=:palaceId")
    fun getScheduleByPalaceId(palaceId: Long): List<Schedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateSchedule(schedule: Schedule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateSchedules(schedules: List<Schedule>)

}