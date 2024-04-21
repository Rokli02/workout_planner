package hu.jszf.marko.workoutplanner.presentation

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home")
    data object CreateActivityScreen : Screen("create_activity")
    data object ActivityTodayScreen : Screen("no_activity")
    data object WorkoutActivityScreen : Screen("wo_activity")
    data object AllActivityScreen : Screen("all_wo_activity")
    data object CreateExerciseScreen : Screen("create_exercise")
    data object AllExerciseScreen : Screen("all_exercise")
    data object ExerciseScreen : Screen("exercise")

    companion object {
        fun getOptionalArgs(parameters: Map<String, Any?>): String {
            val sb = StringBuilder("?")

            for ((key, value) in parameters) {
                sb.append("$key=$value&")
            }

            return sb.toString().trimEnd('&')
        }
    }
}