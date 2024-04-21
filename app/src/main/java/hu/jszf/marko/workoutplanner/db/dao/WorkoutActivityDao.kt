package hu.jszf.marko.workoutplanner.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import hu.jszf.marko.workoutplanner.db.entity.WorkoutActivityEntity
import hu.jszf.marko.workoutplanner.db.entity.relations.ActivityExercise
import hu.jszf.marko.workoutplanner.db.entity.relations.ActivityWithExercises
import hu.jszf.marko.workoutplanner.db.entity.relations.Workout

@Dao
interface WorkoutActivityDao {
    @Transaction
    @Query("SELECT * FROM WorkoutActivityEntity WHERE name LIKE '%' || :filter ||'%' OR date LIKE '%' || :filter ||'%' ORDER BY date DESC")
    fun findByFilterPaged(filter: String): PagingSource<Int, Workout>

    @Query("SELECT * FROM WorkoutActivityEntity WHERE id = :id LIMIT 1")
    suspend fun findById(id: Long): WorkoutActivityEntity?

    @Query("SELECT et.id as id, et.name as name, et.description as description, et.img_id as imgId, aejt.activity_id as activityId, aejt.sets as sets, aejt.reps as reps, aejt.weights as weights " +
            "FROM ExerciseEntity as et JOIN ActivityExerciseJoinTable as aejt ON et.id = aejt.exercise_id " +
            "WHERE aejt.activity_id = :activityId")
    suspend fun findActivityExercises(activityId: Long): List<ActivityExercise>

    @Transaction
    suspend fun findWithExercises(activityId: Long): ActivityWithExercises? {
        val activity = findById(activityId) ?: return null

        val exercises = findActivityExercises(activity.id!!)

        return ActivityWithExercises(
            activity = activity,
            exercises = exercises,
        )
    }

    @Transaction
    @Query("SELECT * FROM WorkoutActivityEntity ORDER BY date DESC LIMIT :last")
    suspend fun findLastWorkout(last: Int): List<Workout>

    @Query("DELETE FROM ActivityExerciseJoinTable WHERE activity_id = :activityId AND exercise_id = :exerciseId")
    suspend fun deleteExercise(activityId: Long, exerciseId: Long)

    @Query("SELECT * FROM WorkoutActivityEntity WHERE date = date('now') ORDER BY date DESC LIMIT 1")
    suspend fun findToday(): WorkoutActivityEntity?

    @Transaction
    suspend fun findTodayWithExercises(): ActivityWithExercises? {
        val activity = findToday() ?: return null

        val exercises = findActivityExercises(activity.id!!)

        return ActivityWithExercises(
            activity = activity,
            exercises = exercises,
        )
    }

    @Insert
    suspend fun save(woActivity: WorkoutActivityEntity): Long

    @Query("DELETE FROM WorkoutActivityEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(woActivity: WorkoutActivityEntity)
}