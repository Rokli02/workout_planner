package hu.jszf.marko.workoutplanner.db.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import hu.jszf.marko.workoutplanner.db.dao.ExerciseDao
import hu.jszf.marko.workoutplanner.model.Exercise
import kotlinx.coroutines.flow.map

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    suspend fun getById(id: Long): Exercise? {
        return exerciseDao.findById(id)?.let { Exercise.fromEntity(it) }
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

    suspend fun save(exercise: Exercise): Boolean {
        return try {
            exerciseDao.save(exercise.toEntity())

            true
        }catch (re: RuntimeException) {
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