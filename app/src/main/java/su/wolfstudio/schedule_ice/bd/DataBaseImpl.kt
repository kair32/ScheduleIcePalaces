package su.wolfstudio.schedule_ice.bd

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.model.ScheduleType
import java.time.LocalDate

class DataBaseImpl (
    database: AppRoomDatabase
) : DataBase {
    private val scheduleDao: ScheduleDao = database.scheduleDao()
    private val athleteDao: AthleteDao = database.athleteDao()
    override fun getSchedules(palaceId: Long): List<Schedule> =
        scheduleDao.getScheduleByPalaceId(palaceId)

    override fun getSchedulesStream(palaceId: Long): Flow<List<Schedule>> =
        scheduleDao.getScheduleByPalaceIdStream(palaceId)

    override fun updateSchedule(schedules: Schedule) {
        scheduleDao.updateSchedule(schedules)
    }

    override fun updateTime(scheduleId: Int, startTime: Int, endTime: Int) {
        scheduleDao.updateTime(scheduleId, startTime, endTime)
    }

    override fun removeScheduleByIds(removeScheduleIds: List<Int>) {
        scheduleDao.removeScheduleByIds(removeScheduleIds)
    }

    override fun removeScheduleById(removeScheduleId: Int) {
        scheduleDao.removeScheduleById(removeScheduleId)
    }

    override fun getSchedulePeriodDate(startDate: LocalDate, endDate: LocalDate): List<Schedule> =
        scheduleDao.getSchedulePeriodDate(startDate.toEpochDay(), endDate.toEpochDay())

    override fun getSchedulePeriodDateStream(startDate: LocalDate, endDate: LocalDate): Flow<List<Schedule>> =
        scheduleDao.getSchedulePeriodDateStream(startDate.toEpochDay(), endDate.toEpochDay())


    override fun getAthletes(): Flow<List<Athlete>> =
        athleteDao.getAthleteStream()

    override fun getAthleteById(athleteId: Int): Athlete? =
        athleteDao.getAthleteById(athleteId)

    override fun updateAthlete(athlete: Athlete) {
        athleteDao.updateSchedule(athlete)
    }

    override fun removeAthleteById(athleteId: Int) =
        athleteDao.removeAthleteById(athleteId)
}