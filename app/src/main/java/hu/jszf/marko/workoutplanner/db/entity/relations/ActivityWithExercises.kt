package hu.jszf.marko.workoutplanner.db.entity.relations

import hu.jszf.marko.workoutplanner.db.entity.WorkoutActivityEntity

data class ActivityWithExercises(
    val activity: WorkoutActivityEntity,
    val exercises: List<ActivityExercise>,
)