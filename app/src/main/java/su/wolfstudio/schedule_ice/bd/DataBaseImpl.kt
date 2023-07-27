package su.wolfstudio.schedule_ice.bd

import kotlinx.coroutines.flow.Flow
import su.wolfstudio.schedule_ice.model.Schedule

class DataBaseImpl (
    database: AppRoomDatabase
) : DataBase {
    private val scheduleDao: ScheduleDao = database.scheduleDao()
    override fun getSchedules(palaceId: Long): List<Schedule> =
        scheduleDao.getScheduleByPalaceId(palaceId)

    override fun getSchedulesStream(palaceId: Long): Flow<List<Schedule>> =
        scheduleDao.getScheduleByPalaceIdStream(palaceId)

    override fun updateSchedules(schedules: List<Schedule>) =
        scheduleDao.updateSchedules(schedules)

}