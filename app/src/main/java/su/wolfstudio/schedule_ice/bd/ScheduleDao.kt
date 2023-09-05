package su.wolfstudio.schedule_ice.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.model.ScheduleType

@Dao
interface ScheduleDao {

    @Query("SELECT * from schedule WHERE palaceId=:palaceId")
    fun getScheduleByPalaceIdStream(palaceId: Long): Flow<List<Schedule>>

    @Query("SELECT * from schedule WHERE palaceId=:palaceId")
    fun getScheduleByPalaceId(palaceId: Long): List<Schedule>

    @Query("SELECT * from schedule WHERE date>=:startDate AND date<=:endDate")
    fun getSchedulePeriodDate(startDate: Long, endDate: Long): List<Schedule>

    @Query("SELECT * from schedule WHERE date>=:startDate AND date<=:endDate")
    fun getSchedulePeriodDateStreamOld(startDate: Long, endDate: Long): Flow<List<Schedule>>

    @Query("SELECT * from schedule WHERE date>=:startDate AND date<=:endDate")
    fun getSchedulePeriodDateStream(startDate: Long, endDate: Long): Flow<List<Schedule>>

    @Query("DELETE from schedule WHERE id IN (:scheduleIds)")
    fun removeScheduleByIds(scheduleIds: List<Int>)

    @Query("DELETE from schedule WHERE id=:scheduleIds")
    fun removeScheduleById(scheduleIds: Int)

    @Query("UPDATE schedule SET startTime = :startTime ,endTime= :endTime WHERE id LIKE :scheduleId ")
    fun updateTime(scheduleId: Int, startTime: Int, endTime: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateSchedule(schedule: Schedule)

}