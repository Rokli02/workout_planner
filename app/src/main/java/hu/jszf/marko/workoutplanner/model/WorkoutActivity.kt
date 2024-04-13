package hu.jszf.marko.workoutplanner.model

import hu.jszf.marko.workoutplanner.R
import hu.jszf.marko.workoutplanner.db.entity.WorkoutActivityEntity
import java.util.Calendar

data class WorkoutActivity(
    val id: Long?,
    val name: String,
    val date: Calendar = Calendar.getInstance(),
    val imgId: Int = R.drawable.muscles_unknown,
    val isNew: Boolean = false,
    var allExercises: Int = 0,
    var allSets: Int = 0,
) {
    fun toEntity(): WorkoutActivityEntity {
        return WorkoutActivityEntity(
            id = id,
            name = name,
            date = date,
            imgId = imgId.let { if (it == R.drawable.muscles_unknown) null else it },
            allExercises = allExercises,
            allSets = allSets
        )
    }

    companion object {
        fun fromEntity(entity: WorkoutActivityEntity): WorkoutActivity {
            return WorkoutActivity(
                id = entity.id,
                name = entity.name,
                date = entity.date,
                imgId = entity.imgId ?: R.drawable.muscles_unknown,
                allExercises = entity.allExercises,
                allSets = entity.allSets,
                isNew = false,
            )
        }
    }
}
