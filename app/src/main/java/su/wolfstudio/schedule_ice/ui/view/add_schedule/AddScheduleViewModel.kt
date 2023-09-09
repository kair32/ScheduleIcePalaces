package su.wolfstudio.schedule_ice.ui.view.add_schedule

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Palace
import su.wolfstudio.schedule_ice.model.Schedule
import java.time.LocalDate

interface AddScheduleViewModel {
    val palace : StateFlow<Palace>
    val schedules : StateFlow<List<Schedule>>
    val currentDate : StateFlow<LocalDate>

    fun chooseCurrentDate(date: LocalDate)
    fun onAddSchedule()
    fun onUpdateTime(scheduleId: Int, startTime: Int, endTime: Int)
    fun onRemoveSchedule(scheduleId: Int)
    fun onUpdateSchedule(schedule: Schedule)
}