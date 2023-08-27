package su.wolfstudio.schedule_ice.ui.view.add_schedule

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.bd.DataBase
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Palace
import su.wolfstudio.schedule_ice.model.Schedule
import su.wolfstudio.schedule_ice.utils.componentCoroutineScope
import java.time.LocalDate

class RealAddScheduleComponent(
    componentContext: ComponentContext,
    private val palacesId: Long
) : ComponentContext by componentContext, AddScheduleComponent {
    private val cash = getDependency<ApplicationCache>()
    private val db = getDependency<DataBase>()
    private val coroutineScope = componentCoroutineScope()

    override val palace: StateValue<Palace> by stateValue(cash.listPalace.value.find { it.id == palacesId }!!)
    override val schedules: StateValue<List<Schedule>> by stateValue(listOf())
    override val currentDate: StateValue<LocalDate> by stateValue(LocalDate.now())

    init {
        coroutineScope.launch(IO) {
            db.getSchedulesStream(palacesId)
                .collect{ schedules.emit(it) }
        }
    }

    override fun chooseCurrentDate(date: LocalDate) {
        currentDate.compareAndSet(currentDate.value, date)
    }

    override fun onAddSchedule() {
        coroutineScope.launch(IO) {
            db.updateSchedule(
                Schedule(
                    palaceId = palacesId,
                    date = currentDate.value
                )
            )
        }
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