package su.wolfstudio.schedule_ice.ui.view.add_schedule

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Palaces
import su.wolfstudio.schedule_ice.model.Schedule
import java.time.LocalDate

interface AddScheduleComponent {
    val palace : StateFlow<Palaces>
    val schedules : StateFlow<List<Schedule>>
    val currentDate : StateFlow<LocalDate>

    fun onSave()
    fun chooseCurrentDate(date: LocalDate)
    fun onAddSchedule()
    fun onUpdateTime(scheduleId: Int, startTime: Int, endTime: Int)
    fun onRemoveSchedule(scheduleId: Int)
}