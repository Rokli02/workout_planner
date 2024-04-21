package hu.jszf.marko.workoutplanner.db.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import hu.jszf.marko.workoutplanner.db.entity.ActivityExerciseJoinTable
import hu.jszf.marko.workoutplanner.db.entity.WorkoutActivityEntity

data class Workout (
    @Embedded
    val activity: WorkoutActivityEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "activity_id"
    )
    val exercises: List<ActivityExerciseJoinTable>
)