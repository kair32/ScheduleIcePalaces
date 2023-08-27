package su.wolfstudio.schedule_ice.ui.schedule

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.DateWithSchedulePalace
import java.time.LocalDate

interface ScheduleComponent {
    val palacesSchedules: StateFlow<List<DateWithSchedulePalace>>
    val currentWeek: StateFlow<Pair<LocalDate, LocalDate>>

    fun onNextWeek()
    fun onPreviousWeek()

    fun onUpdateTime(scheduleId: Int, startTime: Int, endTime: Int)
    fun onRemoveSchedule(scheduleId: Int)
}