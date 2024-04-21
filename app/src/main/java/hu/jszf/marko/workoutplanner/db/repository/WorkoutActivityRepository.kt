package hu.jszf.marko.workoutplanner.db.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import hu.jszf.marko.workoutplanner.db.dao.ActivityExerciseJoinTableDao
import hu.jszf.marko.workoutplanner.db.dao.WorkoutActivityDao
import hu.jszf.marko.workoutplanner.model.Exercise
import hu.jszf.marko.workoutplanner.model.ActivityExercise
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkoutActivityRepository(
    private val workoutActivityDao: WorkoutActivityDao,
    private val activityExerciseJoinTableDao: ActivityExerciseJoinTableDao
) {
    suspend fun getLast(last: Int): List<WorkoutActivity> {
        return workoutActivityDao.findLastWorkout(last).map { WorkoutActivity.fromEntity(it) }
    }

    suspend fun getToday(): WorkoutActivity? {
        return workoutActivityDao.findTodayWithExercises()?.let {
            WorkoutActivity.fromEntity(it)
        }
    }

    suspend fun getById(id: Long): WorkoutActivity? {
        return workoutActivityDao.findWithExercises(id)?.let {
            WorkoutActivity.fromEntity(it)
        }
    }

    fun getByFilterPaged(filter: String): Flow<PagingData<WorkoutActivity>> = Pager(
        PagingConfig(10, 5)
    ) {
        workoutActivityDao.findByFilterPaged(filter)
    }.flow.map { value ->
        value.map {woEntity ->
            WorkoutActivity.fromEntity(woEntity)
        }
    }

    suspend fun save(workoutActivity: WorkoutActivity): Long? {
        return try {
            val activityId = workoutActivityDao.save(workoutActivity.toEntity())

            workoutActivity.exercises?.also {
                insertActivityExercise(it, activityId)
            }

            activityId
        } catch (e: RuntimeException) {
            println("WorkoutActivityRepository save($workoutActivity) failed")

            null
        }
    }

    suspend fun update(workoutActivity: WorkoutActivity): Boolean {
        return try {
            workoutActivityDao.update(workoutActivity.toEntity())

            workoutActivity.exercises?.also { exercises ->
                workoutActivity.id?.also {
                    insertActivityExercise(exercises, it)
                }
            }

            true
        } catch (e: RuntimeException) {
            println("WorkoutActivityRepository update($workoutActivity) failed")

            false
        }
    }

    suspend fun removeActivityExercise(removables: List<Pair<Long, Long>>): Int {
        var deleted = 0

        for ((activityId, exerciseId) in removables) {
            try {
                activityExerciseJoinTableDao.remove(activityId, exerciseId)

                deleted++
            } catch (e: RuntimeException) {
                println("WorkoutActivityRepository removeActivityExercise($activityId, $exerciseId) failed")
            }
        }

        return deleted
    }

    suspend fun deleteById(id: Long): Boolean {
        return try {
            workoutActivityDao.deleteById(id)

            true
        } catch (re: RuntimeException) {
            println("WorkoutActivityRepository deleteById($id) failed")

            false
        }
    }

    private suspend fun insertActivityExercise(exercises: List<Exercise>, activityId: Long): Boolean {
        return try {
            exercises.run {
                map { ActivityExercise(
                    activityId = activityId,
                    exerciseId = it.id!!,
                    sets = 0,
                    reps = mutableListOf(),
                    weights = mutableListOf()
                ) }
            }.also { activityExercises ->
                activityExerciseJoinTableDao.insert(activityExercises.map{ activityExercise -> activityExercise.toEntity() })
            }


            true
        } catch (e: RuntimeException) {
            println("WorkoutActivityRepository insertActivityExercise(size(${exercises.size}), $activityId) failed")

            false
        }
    }
}