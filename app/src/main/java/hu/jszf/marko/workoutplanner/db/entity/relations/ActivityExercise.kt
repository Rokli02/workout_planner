package hu.jszf.marko.workoutplanner.db.entity.relations

data class ActivityExercise(
    val id: Long?,
    val activityId: Long,
    val name: String,
    val description: String,
    val imgId: Int? = null,
    var sets: Int,
    var reps: String?,
    var weights: String?,
)