package hu.jszf.marko.workoutplanner.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.jszf.marko.workoutplanner.db.entity.ExerciseEntity
import hu.jszf.marko.workoutplanner.db.entity.relations.ActivityExercise

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM ExerciseEntity WHERE id = :id LIMIT 1")
    suspend fun findById(id: Long): ExerciseEntity?

    @Query("SELECT * FROM ExerciseEntity WHERE id IN (:ids)")
    suspend fun findByIds(ids: List<Long>): List<ExerciseEntity>

    @Query("SELECT et.id as id, et.name as name, et.description as description, et.img_id as imgId, aejt.activity_id as activityId, aejt.sets as sets, aejt.reps as reps, aejt.weights as weights " +
            "FROM ExerciseEntity as et JOIN ActivityExerciseJoinTable as aejt ON et.id = aejt.exercise_id " +
            "WHERE aejt.activity_id = :activityId AND et.id = :exerciseId LIMIT 1")
    suspend fun findActivityExercise(exerciseId: Long, activityId: Long): ActivityExercise?

    @Query("SELECT * FROM ExerciseEntity WHERE name LIKE '%' || :filter ||'%' ORDER BY name DESC")
    fun findByFilterPaged(filter: String): PagingSource<Int, ExerciseEntity>

    @Query("SELECT * FROM ExerciseEntity WHERE id NOT IN(:exercises) AND name LIKE '%' || :filter ||'%' ORDER BY name DESC")
    fun findByFilterPagedWithoutExercises(filter: String, exercises: List<Long>): PagingSource<Int, ExerciseEntity>

    @Insert
    suspend fun save(exercise: ExerciseEntity)

    @Query("DELETE FROM ExerciseEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(exercise: ExerciseEntity)
}