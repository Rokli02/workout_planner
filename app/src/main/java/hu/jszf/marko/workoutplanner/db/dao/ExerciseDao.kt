package hu.jszf.marko.workoutplanner.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.jszf.marko.workoutplanner.db.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM ExerciseEntity WHERE id = :id LIMIT 1")
    suspend fun findById(id: Long): ExerciseEntity?

    @Query("SELECT * FROM ExerciseEntity WHERE name LIKE '%' || :filter ||'%' ORDER BY name DESC")
    fun findByFilterPaged(filter: String): PagingSource<Int, ExerciseEntity>

    @Insert
    suspend fun save(exercise: ExerciseEntity)

    @Query("DELETE FROM ExerciseEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(exercise: ExerciseEntity)
}