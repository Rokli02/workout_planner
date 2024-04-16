package hu.jszf.marko.workoutplanner.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.jszf.marko.workoutplanner.db.entity.WorkoutActivityEntity

@Dao
interface WorkoutActivityDao {
    @Query("SELECT * FROM WorkoutActivityEntity WHERE name LIKE '%' || :filter ||'%' OR date LIKE '%' || :filter ||'%' ORDER BY date DESC")
    fun findByFilterPaged(filter: String): PagingSource<Int, WorkoutActivityEntity>

    @Query("SELECT * FROM WorkoutActivityEntity WHERE id = :id LIMIT 1")
    suspend fun findById(id: Long): WorkoutActivityEntity?

    @Query("SELECT * FROM WorkoutActivityEntity ORDER BY date DESC LIMIT :last")
    suspend fun findLast(last: Int): List<WorkoutActivityEntity>

    @Query("SELECT * FROM WorkoutActivityEntity WHERE date = date('now') ORDER BY date DESC LIMIT 1")
    suspend fun findToday(): WorkoutActivityEntity?

    @Insert
    suspend fun save(woActivity: WorkoutActivityEntity)

    @Query("DELETE FROM WorkoutActivityEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(woActivity: WorkoutActivityEntity)
}