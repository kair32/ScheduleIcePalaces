package su.wolfstudio.schedule_ice.ui.schedule

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.bd.DataBase
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.DateWithSchedulePalace
import su.wolfstudio.schedule_ice.model.Palace
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.ui.view.add_schedule.Date
import su.wolfstudio.schedule_ice.utils.componentCoroutineScope
import java.time.LocalDate

class RealScheduleComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, ScheduleComponent {
    private val cash = getDependency<ApplicationCache>()
    private val db = getDependency<DataBase>()
    private val coroutineScope = componentCoroutineScope()

    private val palace: List<Palace> = cash.listPalace.value
    val schedules: StateValue<List<Schedule>> by stateValue(listOf())
    override val palacesSchedules: StateValue<List<DateWithSchedulePalace>> by stateValue(listOf())
    override val currentWeek: StateValue<Pair<LocalDate, LocalDate>> by stateValue(Date.getDateByWeekend(isNext = false))

    init {
        coroutineScope.launch(IO) {
            currentWeek.collect { date ->
                launch {
                    db.getSchedulePeriodDateStream(startDate = date.first, endDate = date.second)
                        .collect { schedulePeriodByDate ->
                            val palacesSchedulesNew = mutableListOf<DateWithSchedulePalace>()
                            for (i in 0..6) {
                                val day = date.first.plusDays(i.toLong())
                                val schedules = schedulePeriodByDate.filter { it.date == day }
                                val palaces =
                                    palace.filter { palace -> schedules.any { schedule -> palace.id == schedule.palaceId } }
                                palacesSchedulesNew.add(
                                    DateWithSchedulePalace(
                                        date = day,
                                        palaces = palaces,
                                        schedules = schedules
                                    )
                                )
                            }
                            palacesSchedules.emit(palacesSchedulesNew)
                        }
                }
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
                startDate = currentWeek.value.first,
                isNext = false
            )
        )
    }

    override fun onUpdateTime(scheduleId: Int, startTime: Int, endTime: Int) {
        coroutineScope.launch(IO) {
            db.updateTime(scheduleId, startTime, endTime)
        }
    }

    override fun onRemoveSchedule(scheduleId: Int) {
        coroutineScope.launch(IO) {
            db.removeScheduleById(scheduleId)
        }
    }
}