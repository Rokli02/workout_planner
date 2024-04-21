package hu.jszf.marko.workoutplanner.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "ActivityExerciseJoinTable",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutActivityEntity::class,
            parentColumns = ["id"],
            childColumns = ["activity_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    primaryKeys = ["activity_id", "exercise_id"],
)
data class ActivityExerciseJoinTable(
    @ColumnInfo(name = "activity_id")
    val activityId: Long,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(defaultValue = "0")
    val sets: Int,
    val reps: String?,
    val weights: String?,
)