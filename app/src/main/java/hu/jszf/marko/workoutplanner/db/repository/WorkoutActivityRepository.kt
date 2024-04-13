package hu.jszf.marko.workoutplanner.db.repository

import hu.jszf.marko.workoutplanner.db.dao.WorkoutActivityDao
import hu.jszf.marko.workoutplanner.model.WorkoutActivity

class WorkoutActivityRepository(private val workoutActivityDao: WorkoutActivityDao) {
    suspend fun getLast(last: Int): List<WorkoutActivity> {
        return workoutActivityDao.findLast(last).map { WorkoutActivity.fromEntity(it) }
    }

    suspend fun getById(id: Long): WorkoutActivity? {
        return workoutActivityDao.findById(id)?.let {
            WorkoutActivity.fromEntity(it)
        }
    }

    suspend fun save(workoutActivity: WorkoutActivity): Boolean {
        println("workoutActivity: $workoutActivity")
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