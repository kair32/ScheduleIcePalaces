package su.wolfstudio.schedule_ice.ui.view.add_schedule

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.bd.DataBase
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Palaces
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

    override val palace: StateValue<Palaces> by stateValue(cash.listPalaces.value.find { it.id == palacesId }!!)
    override val schedules: StateValue<List<Schedule>> by stateValue(listOf())
    override val currentDate: StateValue<LocalDate> by stateValue(LocalDate.now())

    init {
        coroutineScope.launch(IO) {
            schedules.emit(db.getSchedules(palacesId))
        }
    }

    override fun onSave() {
        coroutineScope.launch(IO) {
            db.updateSchedules(schedules.value)
        }
    }

    override fun chooseCurrentDate(date: LocalDate) {
        currentDate.compareAndSet(currentDate.value, date)
    }

    override fun onAddSchedule() {
        val listUpdate = schedules.value.toMutableList()
        val id = (listUpdate.maxByOrNull { it.id }?.id ?: -1).plus(1)
        listUpdate.add(
            Schedule(
                id = id,
                palaceId = palacesId,
                date = currentDate.value
            )
        )
        schedules.compareAndSet(schedules.value, listUpdate)
    }

    override fun onUpdateTime(scheduleId: Int, startTime: Int, endTime: Int) {
        schedules.value.find { it.id == scheduleId }?.apply {
            this.startTime = startTime
            this.endTime = endTime
        }
    }

    override fun onRemoveSchedule(scheduleId: Int) {
        val schedulesUpdate = schedules.value.toMutableList()
        schedulesUpdate.removeAll { it.id == scheduleId }
        schedules.compareAndSet(schedules.value, schedulesUpdate)
    }

}