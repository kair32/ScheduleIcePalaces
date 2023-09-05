package su.wolfstudio.schedule_ice.ui.view.add_schedule

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import su.wolfstudio.schedule_ice.ui.base.ViewModelBase
import java.time.LocalDate

class AddScheduleViewModelImpl(
    private val palacesId: Long
) : ViewModelBase(), AddScheduleViewModel {
    private val cash = getDependency<ApplicationCache>()
    private val db = getDependency<DataBase>()

    override val palace: StateValue<Palace> by stateValue(cash.listPalace.value.find { it.id == palacesId }!!)
    override val schedules: StateValue<List<Schedule>> by stateValue(listOf())
    override val currentDate: StateValue<LocalDate> by stateValue(LocalDate.now())

    init {
        viewModelScope.launch(IO) {
            db.getSchedulesStream(palacesId)
                .collect{ schedules.emit(it) }
        }
    }

    override fun chooseCurrentDate(date: LocalDate) {
        currentDate.compareAndSet(currentDate.value, date)
    }

    override fun onAddSchedule() {
        viewModelScope.launch(IO) {
            db.updateSchedule(
                Schedule(
                    palaceId = palacesId,
                    date = currentDate.value
                )
            )
        }
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