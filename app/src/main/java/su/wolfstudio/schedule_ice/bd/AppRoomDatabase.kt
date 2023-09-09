package su.wolfstudio.schedule_ice.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import su.wolfstudio.schedule_ice.bd.MigrationDB.MIGRATION_4_5
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.model.DatabaseLocalDateConverter
import su.wolfstudio.schedule_ice.model.ListIntegerConverter
import su.wolfstudio.schedule_ice.model.Schedule


@Database(
    entities = [
        Schedule::class,
        Athlete::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(
    DatabaseLocalDateConverter::class, ListIntegerConverter::class
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
    abstract fun athleteDao(): AthleteDao

    companion object {
        private const val DATABASE_NAME = "schedule_ice"

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppRoomDatabase::class.java, DATABASE_NAME)
                //.fallbackToDestructiveMigration()
                .addMigrations(MigrationDB.MIGRATION_3_4, MIGRATION_4_5)
                .build()
    }
}