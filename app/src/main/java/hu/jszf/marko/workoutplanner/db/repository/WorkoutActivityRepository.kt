package hu.jszf.marko.workoutplanner.db.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import hu.jszf.marko.workoutplanner.db.dao.WorkoutActivityDao
import hu.jszf.marko.workoutplanner.model.WorkoutActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WorkoutActivityRepository(private val workoutActivityDao: WorkoutActivityDao) {
    suspend fun getLast(last: Int): List<WorkoutActivity> {
        return workoutActivityDao.findLast(last).map { WorkoutActivity.fromEntity(it) }
    }

    suspend fun getById(id: Long): WorkoutActivity? {
        return workoutActivityDao.findById(id)?.let {
            WorkoutActivity.fromEntity(it)
        }
    }

    suspend fun getToday(): WorkoutActivity? {
        return workoutActivityDao.findToday()?.let {
            WorkoutActivity.fromEntity(it)
        }
    }

    fun getByFilterPaged(filter: String): Flow<PagingData<WorkoutActivity>> = Pager(
        PagingConfig(10, 15)
    ) {
        workoutActivityDao.findByFilterPaged(filter)
    }.flow.map { value ->
        value.map {woEntity ->
            WorkoutActivity.fromEntity(woEntity)
        }
    }

    suspend fun save(workoutActivity: WorkoutActivity): Boolean {
        return try {
            workoutActivityDao.save(workoutActivity.toEntity())

            true
        } catch (e: RuntimeException) {
            println("WorkoutActivityRepository save($workoutActivity) failed")

            false
        }
    }

    suspend fun update(workoutActivity: WorkoutActivity): Boolean {
        return try {
            workoutActivityDao.update(workoutActivity.toEntity())

            true
        } catch (e: RuntimeException) {
            println("WorkoutActivityRepository update($workoutActivity) failed")

            false
        }
    }
}