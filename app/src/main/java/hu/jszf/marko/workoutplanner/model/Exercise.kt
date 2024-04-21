package hu.jszf.marko.workoutplanner.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import hu.jszf.marko.workoutplanner.R
import hu.jszf.marko.workoutplanner.db.entity.ExerciseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise (
    val id: Long?,
    val name: String,
    val description: String,
    @ColumnInfo(name = "img_id")
    val imgId: Int? = R.drawable.muscles_unknown,
    val activityExercise: ActivityExercise? = null,
): Parcelable {
    fun toEntity(): ExerciseEntity  {
        return ExerciseEntity(
            id = id,
            name = name,
            description = description,
            imgId = imgId,
        )
    }

    companion object {
        fun fromEntity(entity: ExerciseEntity): Exercise {
            return Exercise(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                imgId = entity.imgId,
            )
        }

        fun fromEntity(activityExercise: hu.jszf.marko.workoutplanner.db.entity.relations.ActivityExercise): Exercise {
            return Exercise(
                id = activityExercise.id,
                name = activityExercise.name,
                description = activityExercise.description,
                imgId = activityExercise.imgId,
                activityExercise = ActivityExercise.fromEntity(activityExercise),
            )
        }
    }
}