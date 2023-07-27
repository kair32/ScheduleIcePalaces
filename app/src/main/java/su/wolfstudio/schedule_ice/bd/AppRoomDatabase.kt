package su.wolfstudio.schedule_ice.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import su.wolfstudio.schedule_ice.model.DatabaseLocalDateConverter
import su.wolfstudio.schedule_ice.model.Schedule

@Database(
    entities = [
        Schedule::class,
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(
    DatabaseLocalDateConverter::class
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        private const val DATABASE_NAME = "schedule_ice"

        fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppRoomDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}