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
class ListIntegerConverter {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String = intList.toString()
    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        val result = ArrayList<Int>()
        val split =stringList.replace("[","").replace("]","").replace(" ","").split(",")
        for (n in split) {
            try {
                result.add(n.toInt())
            } catch (e: Exception) {

            }
        }
        return result
    }
}