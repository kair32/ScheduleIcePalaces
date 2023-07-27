package su.wolfstudio.schedule_ice.bd

import kotlinx.coroutines.flow.Flow
import su.wolfstudio.schedule_ice.model.Schedule

interface DataBase{
    fun getSchedules(palaceId: Long): List<Schedule>
    fun getSchedulesStream(palaceId: Long): Flow<List<Schedule>>
    fun updateSchedules(schedules: List<Schedule>)
}