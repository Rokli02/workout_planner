package hu.jszf.marko.workoutplanner.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "ActivityExerciseJoinTable",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutActivityEntity::class,
            parentColumns = ["id"],
            childColumns = ["activityId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(name = "activity_exercise_join_FK", value = ["activityId", "exerciseId"], unique = true)
    ]
)
data class ActivityExerciseJoinTable(
    @ColumnInfo(name = "activity_id")
    val activityId: Long,
    @ColumnInfo(name = "exercise_id")
    val exerciseId: Long,
    @ColumnInfo(defaultValue = "0")
    var sets: Int,
    var reps: String?, //;-vel elválasztva
    var weights: String?, //;-vel elválasztva
)