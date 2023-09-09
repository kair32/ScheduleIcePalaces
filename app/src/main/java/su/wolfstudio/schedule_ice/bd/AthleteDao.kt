package su.wolfstudio.schedule_ice.bd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import su.wolfstudio.schedule_ice.model.Athlete

@Dao
interface AthleteDao {

    @Query("SELECT * from athlete WHERE isDelete=0")
    fun getAthleteStream(): Flow<List<Athlete>>

    @Query("SELECT * from athlete WHERE id=:athleteId")
    fun getAthleteById(athleteId: Int): Athlete?

    @Query("UPDATE athlete SET isDelete = :isDelete WHERE id LIKE :athleteId ")
    fun removeAthleteById(athleteId: Int, isDelete: Boolean = true)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateSchedule(athlete: Athlete)
}