package hu.jszf.marko.workoutplanner.db.entity.relations

data class ActivityExercise(
    val id: Long?,
    val activityId: Long,
    val name: String,
    val description: String,
    val imgId: Int? = null,
    val sets: Int,
    val reps: String?,
    val weights: String?,
)