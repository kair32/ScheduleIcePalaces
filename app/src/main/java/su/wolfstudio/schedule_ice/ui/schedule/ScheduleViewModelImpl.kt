package su.wolfstudio.schedule_ice.ui.schedule

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.bd.DataBase
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.model.DateWithSchedulePalace
import su.wolfstudio.schedule_ice.model.Palace
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.ui.base.ViewModelBase
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date
import java.time.LocalDate

class ScheduleViewModelImpl : ViewModelBase(), ScheduleViewModel {
    private val cash = getDependency<ApplicationCache>()
    private val db = getDependency<DataBase>()

    private val palace: List<Palace> = cash.listPalace.value
    override val palacesSchedules: StateValue<List<DateWithSchedulePalace>> by stateValue(listOf())
    override val isLoad: StateValue<Boolean> by stateValue(true)
    override val currentWeek: StateValue<Pair<LocalDate, LocalDate>> by stateValue(Date.getDateByWeekend(isNext = false))

    private val athletes = mutableListOf<Athlete>()

    init {
        observableAthletes()
        viewModelScope.launch(IO) {
            currentWeek.collect { date ->
                launch {
                    db.getSchedulePeriodDateStream(startDate = date.first, endDate = date.second)
                        .collect { schedulePeriodByDate ->
                            val palacesSchedulesNew = mutableListOf<DateWithSchedulePalace>()
                            for (i in 0..6) {
                                val day = date.first.plusDays(i.toLong())
                                val schedules = schedulePeriodByDate.filter { it.date == day }
                                val palaces = palace.filter { palace ->
                                    schedules.any { schedule -> palace.id == schedule.palaceId }
                                }
                                schedules.map { schedule ->
                                    schedule.athletes = athletes.filter { schedule.athleteIds.contains(it.id) }
                                }
                                if (schedules.isNotEmpty())
                                    palacesSchedulesNew.add(
                                        DateWithSchedulePalace(
                                            date = day,
                                            palaces = palaces,
                                            schedules = schedules
                                        )
                                    )
                            }
                            isLoad.emit(false)
                            palacesSchedules.emit(palacesSchedulesNew)
                        }
                }
            }
        }
    }

    private fun observableAthletes(){
        viewModelScope.launch(IO) {
            db.getAthletes().collect{
                athletes.clear()
                athletes.addAll(it)
            }
        }
    }

    override fun onNextWeek() {
        currentWeek.compareAndSet(
            currentWeek.value,
            Date.getDateByWeekend(startDate = currentWeek.value.first)
        )
    }

    override fun onPreviousWeek() {
        currentWeek.compareAndSet(
            currentWeek.value,
            Date.getDateByWeekend(
                startDate = currentWeek.value.first.minusDays(1),
                isNext = false
            )
        )
    }

    override fun onUpdateTime(scheduleId: Int, startTime: Int, endTime: Int) {
        viewModelScope.launch(IO) {
            db.updateTime(scheduleId, startTime, endTime)
        }
    }

    override fun onRemoveSchedule(scheduleId: Int) {
        viewModelScope.launch(IO) {
            db.removeScheduleById(scheduleId)
        }
    }

    override fun onUpdateSchedule(schedule: Schedule) {
        viewModelScope.launch(IO) {
            db.updateSchedule(schedule)
        }
    }
}