package hu.jszf.marko.workoutplanner.db.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import hu.jszf.marko.workoutplanner.db.dao.ActivityExerciseJoinTableDao
import hu.jszf.marko.workoutplanner.db.dao.ExerciseDao
import hu.jszf.marko.workoutplanner.model.ActivityExercise
import hu.jszf.marko.workoutplanner.model.Exercise
import kotlinx.coroutines.flow.map

class ExerciseRepository(
    private val exerciseDao: ExerciseDao,
    private val activityExerciseJoinTableDao: ActivityExerciseJoinTableDao
) {
    suspend fun getById(id: Long): Exercise? {
        return exerciseDao.findById(id)?.let { Exercise.fromEntity(it) }
    }

    suspend fun getByIds(ids: List<Long>): List<Exercise> {
        return exerciseDao.findByIds(ids).map { Exercise.fromEntity(it) }
    }

    fun getByFilterPaged(filter: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 15,
        )
    ) {
        exerciseDao.findByFilterPaged(filter)
    }.flow.map { value ->
        value.map { entity ->
            Exercise.fromEntity(entity)
        }
    }

    fun getByFilterPagedWithoutExercises(filter: String, exercises: List<Long>) = Pager(
        config = PagingConfig(
            pageSize = 10,
            prefetchDistance = 5,
        )
    ) {
        exerciseDao.findByFilterPagedWithoutExercises(filter, exercises)
    }.flow.map { value ->
        value.map { entity ->
            Exercise.fromEntity(entity)
        }
    }

    suspend fun getByExerciseAndActivity(exerciseId: Long, activityId: Long): Exercise? {
        return exerciseDao.findActivityExercise(exerciseId, activityId)?.let { Exercise.fromEntity(it) }
    }

    suspend fun save(exercise: Exercise): Boolean {
        return try {
            exerciseDao.save(exercise.toEntity())

            true
        }catch (re: RuntimeException) {
            false
        }
    }

    suspend fun updateActivityExercise(activityExercise: ActivityExercise): Boolean {
        return try {
            activityExerciseJoinTableDao.update(activityExercise.toEntity())

            true
        } catch (re: RuntimeException) {
            false
        }
    }

    suspend fun update(exercise: Exercise): Boolean {
        return try {
            exerciseDao.update(exercise.toEntity())

            true
        } catch (re: RuntimeException) {
            false
        }
    }
}