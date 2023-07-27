package su.wolfstudio.schedule_ice.ui.view.add_schedule

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.util.Locale

object Date {

    fun getDate(): List<LocalDate> {
        val startDate = LocalDate.now()
        val dates = mutableListOf<LocalDate>()

        for (i in 0..99) {
            val dateToAdd = startDate.plusDays(i.toLong())
            dates.add(dateToAdd)
        }
        return dates
    }

    fun DayOfWeek.getShortDisplayName(): String = getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault())

    fun Month?.getFullDisplayName() = this?.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()) ?:""
}