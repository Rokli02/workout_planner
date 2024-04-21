package hu.jszf.marko.workoutplanner.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.jszf.marko.workoutplanner.db.entity.ActivityExerciseJoinTable

@Dao
interface ActivityExerciseJoinTableDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entities: List<ActivityExerciseJoinTable>)

    @Update
    suspend fun update(activityExercise: ActivityExerciseJoinTable)

    @Query("DELETE FROM ActivityExerciseJoinTable WHERE activity_id = :activityId AND exercise_id = :exerciseId")
    suspend fun remove(activityId: Long, exerciseId: Long)
}