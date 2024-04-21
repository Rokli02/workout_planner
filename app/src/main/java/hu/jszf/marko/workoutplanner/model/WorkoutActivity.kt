package hu.jszf.marko.workoutplanner.model

import hu.jszf.marko.workoutplanner.R
import hu.jszf.marko.workoutplanner.db.entity.WorkoutActivityEntity
import hu.jszf.marko.workoutplanner.db.entity.relations.ActivityWithExercises
import hu.jszf.marko.workoutplanner.db.entity.relations.Workout
import java.util.Calendar

data class WorkoutActivity(
    val id: Long?,
    val name: String,
    val date: Calendar = Calendar.getInstance(),
    val imgId: Int = R.drawable.muscles_unknown,
    val isNew: Boolean = false,
    var allExercises: Int = 0,
    var allSets: Int = 0,
    var exercises: List<Exercise>? = null
) {
    fun toEntity(): WorkoutActivityEntity {
        return WorkoutActivityEntity(
            id = id,
            name = name,
            date = date,
            imgId = imgId.let { if (it == R.drawable.muscles_unknown) null else it },
        )
    }

    companion object {
        fun fromEntity(entity: Workout): WorkoutActivity {
            return WorkoutActivity(
                id = entity.activity.id,
                name = entity.activity.name,
                date = entity.activity.date,
                imgId = entity.activity.imgId ?: R.drawable.muscles_unknown,
                allExercises = entity.exercises.size,
                allSets = entity.exercises.sumOf { it.sets },
                isNew = false,
            )
        }

        fun fromEntity(entity: ActivityWithExercises): WorkoutActivity {
            val allSets: Int = entity.exercises.sumOf { activityExercise ->
                activityExercise.sets
            }

            return WorkoutActivity(
                id = entity.activity.id,
                name = entity.activity.name,
                date = entity.activity.date,
                imgId = entity.activity.imgId ?: R.drawable.muscles_unknown,
                allExercises = entity.exercises.size,
                allSets = allSets,
                isNew = false,
                exercises = entity.exercises.map { Exercise.fromEntity(it) }
            )
        }
    }
}
