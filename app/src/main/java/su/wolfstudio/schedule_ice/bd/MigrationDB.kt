package su.wolfstudio.schedule_ice.bd

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import su.wolfstudio.schedule_ice.model.ScheduleType

object MigrationDB {
    val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Athlete` (`id` INTEGER, " +
                    "`name` TEXT," +
                    "`surname` TEXT," +
                    "`middleName` TEXT," +
                    "`birthday` LONG," +
                    "`isDelete` BOOLEAN," +
                    " PRIMARY KEY(`id`))");
        }
    }

    val MIGRATION_4_5: Migration = object : Migration(4, 5){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE schedule ADD COLUMN 'comment' TEXT DEFAULT NULL")
            database.execSQL("ALTER TABLE schedule ADD COLUMN 'additional' INTEGER NOT NULL DEFAULT 0")
            database.execSQL("ALTER TABLE schedule ADD COLUMN 'athleteIds' TEXT NOT NULL DEFAULT '[]'")
            database.execSQL("UPDATE schedule SET type = 'ICE'")
        }
    }
}