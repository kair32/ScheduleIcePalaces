package su.wolfstudio.schedule_ice.model

import java.time.LocalDate

data class DateWithSchedulePalace(
    val date: LocalDate,
    val palaces: List<Palace>,
    val schedules: List<Schedule>
)