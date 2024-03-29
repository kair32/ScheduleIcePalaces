package su.wolfstudio.schedule_ice.ui.view.add_schedule

import su.wolfstudio.schedule_ice.model.DateWithSchedulePalace
import su.wolfstudio.schedule_ice.model.Schedule
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
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

    /**
     * Получит дату начала и конца недели.
     * [startDate] - начало недели откуда ведем отсчет
     * [isNext] - в какую сторону считать
     * true - будущая неделя
     * false - прошлая неделя
     */
    fun getDateByWeekend(
        startDate: LocalDate = LocalDate.now(),
        isNext: Boolean = true
    ): Pair<LocalDate, LocalDate> {
        var startRangeDate = if (isNext) startDate.plusDays(1)
        else startDate

        while (startRangeDate.dayOfWeek != DayOfWeek.MONDAY) {
            startRangeDate = if (isNext)
                startRangeDate.plusDays(1)
            else startRangeDate.minusDays(1)
        }
        val endRangeDate = startRangeDate.plusDays(6)

        return Pair(startRangeDate, endRangeDate)
    }

    /**
     * Вернет дату в формате "28 августа - 03 сентября"
     */
    fun getRangeDate(currentWeek: Pair<LocalDate, LocalDate>): String {
        return currentWeek.first.format(DateTimeFormatter.ofPattern("dd MMM")) + " - " +
                currentWeek.second.format(DateTimeFormatter.ofPattern("dd MMM"))
    }

    /**
     * Вернет дату в формате "28 понедельник"
     */
    fun getDateWithDayOfWeek(date: LocalDate): String =
        date.format(DateTimeFormatter.ofPattern("dd EEEE"))

    /**
     * Вернет дату в формате "28 октября 2003"
     */
    fun getDateWithYear(dateMils: Long): String {
        if (dateMils == 0L) return ""
        val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return formatter.format(Date(dateMils))
    }

    /**
     * Сравнивает 2 даты и возвращает разницу без учета года.
     */
    fun getLeftUntilBirthday(dateMils: Long): Int?{
        if (dateMils == 0L) return null
        val calOld = Calendar.getInstance()
        calOld.time = Date(dateMils)
        val calNow = Calendar.getInstance()

        return calOld.get(Calendar.DAY_OF_YEAR) - calNow.get(Calendar.DAY_OF_YEAR)
    }

    /**
     * Вернет список для копирования в буффер обмена
     */
    fun getCopyFormatSchedule(palacesSchedules: List<DateWithSchedulePalace>): String {
        var string = ""
        palacesSchedules.map { palaceSchedule ->
            if (palaceSchedule.schedules.isEmpty()) return@map
            if (string.isNotEmpty()) string += "\n"
            string += "**" + palaceSchedule.date.format(DateTimeFormatter.ofPattern("EEEE dd.MM")) + "**"
            palaceSchedule.palaces.map { palace ->
                string += "\n" + palace.name
                palaceSchedule.schedules
                    .filter { it.palaceId == palace.id }
                    .map { schedule ->
                        string += "\n" + Schedule.getTimeWithZero(
                            schedule.getStartTimeHour(),
                            schedule.getStartTimeMin()
                        ) + "-" + Schedule.getTimeWithZero(
                            schedule.getEndTimeHour(),
                            schedule.getEndTimeMin()
                        ) + " " + schedule.type.nameRu

                        if (schedule.additional)
                            string += " Доп."

                        schedule.athletes.map {
                            string += "\n" + it.surname + " " + it.name
                        }

                        if (!schedule.comment.isNullOrBlank())
                            string += "\n ${schedule.comment}"
                    }
            }
        }
        return string
    }

    fun DayOfWeek.getShortDisplayName(): String =
        getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault())

    fun Month?.getFullDisplayName() =
        this?.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault()) ?: ""
}