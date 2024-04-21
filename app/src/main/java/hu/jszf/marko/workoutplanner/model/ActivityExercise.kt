package hu.jszf.marko.workoutplanner.model

import android.os.Parcelable
import hu.jszf.marko.workoutplanner.db.entity.ActivityExerciseJoinTable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivityExercise(
    val activityId: Long,
    val exerciseId: Long,
    var sets: Int,
    var reps: MutableList<Int>,
    var weights: MutableList<Int>,
) : Parcelable {
    fun toEntity(): ActivityExerciseJoinTable {
        return ActivityExerciseJoinTable(
            activityId = activityId,
            exerciseId = exerciseId,
            sets = sets,
            reps = reps.run { if (isNotEmpty()) joinToString(separator = ";") else null },
            weights = weights.run { if (isNotEmpty()) joinToString(separator = ";") else null }
        )
    }

    companion object {
        fun fromEntity(activityExercise: hu.jszf.marko.workoutplanner.db.entity.relations.ActivityExercise): ActivityExercise {
            return ActivityExercise(
                activityId = activityExercise.activityId,
                exerciseId = activityExercise.id!!,
                sets = activityExercise.sets,
                reps = activityExercise.reps?.split(";")?.map { it.toInt() }?.toMutableList() ?: mutableListOf(),
                weights = activityExercise.weights?.split(";")?.map { it.toInt() }?.toMutableList() ?: mutableListOf()
            )
        }
    }
}