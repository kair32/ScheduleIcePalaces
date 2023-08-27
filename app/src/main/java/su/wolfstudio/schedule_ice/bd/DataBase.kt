package su.wolfstudio.schedule_ice.bd

import kotlinx.coroutines.flow.Flow
import su.wolfstudio.schedule_ice.model.Schedule
import java.time.LocalDate

interface DataBase{
    fun getSchedules(palaceId: Long): List<Schedule>
    fun getSchedulesStream(palaceId: Long): Flow<List<Schedule>>

    fun getSchedulePeriodDate(startDate: LocalDate, endDate: LocalDate): List<Schedule>
    fun getSchedulePeriodDateStream(startDate: LocalDate, endDate: LocalDate): Flow<List<Schedule>>
    fun removeScheduleByIds(removeScheduleIds: List<Int>)
    fun removeScheduleById(removeScheduleId: Int)
    fun updateSchedules(schedules: List<Schedule>)
    fun updateSchedule(schedules: Schedule)

    fun updateTime(scheduleId: Int, startTime: Int, endTime: Int)
}