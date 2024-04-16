package hu.jszf.marko.workoutplanner.model

import androidx.room.ColumnInfo
import hu.jszf.marko.workoutplanner.R
import hu.jszf.marko.workoutplanner.db.entity.ExerciseEntity

data class Exercise (
    val id: Long?,
    val name: String,
    val description: String,
    @ColumnInfo(name = "img_id")
    val imgId: Int? = R.drawable.muscles_unknown,
) {

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
    }
}