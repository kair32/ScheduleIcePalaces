package su.wolfstudio.schedule_ice.model

import androidx.room.TypeConverter
import java.time.LocalDate


class DatabaseLocalDateConverter{

    @TypeConverter
    fun fromHobbies(hobbies: LocalDate): Long {
        return hobbies.toEpochDay()
    }

    @TypeConverter
    fun toHobbies(data: Long): LocalDate? {
        return LocalDate.ofEpochDay(data)
    }
}