package hu.jszf.marko.workoutplanner.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import hu.jszf.marko.workoutplanner.db.dao.ExerciseDao
import hu.jszf.marko.workoutplanner.db.dao.WorkoutActivityDao
import hu.jszf.marko.workoutplanner.db.entity.ActivityExerciseJoinTable
import hu.jszf.marko.workoutplanner.db.entity.ExerciseEntity
import hu.jszf.marko.workoutplanner.db.entity.WorkoutActivityEntity
import hu.jszf.marko.workoutplanner.utils.DateFormatter
import java.util.Calendar

@Database(
    version = 1,
    entities = [
        WorkoutActivityEntity::class,
        ExerciseEntity::class,
        ActivityExerciseJoinTable::class
    ],
    exportSchema = false,
)
@TypeConverters(WorkoutConverter::class)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun workoutActivityDao(): WorkoutActivityDao
    abstract fun exerciseDao(): ExerciseDao
}

class WorkoutConverter {
    @TypeConverter
    fun fromCalendar(calendar: Calendar): String {
        return DateFormatter.format(calendar.time)
    }

    @TypeConverter
    fun toCalendar(text: String): Calendar {
        return try {
            val (year, month, day) = text.split("-").map { it.toInt() }

            Calendar.getInstance().apply {
                set(year, month - 1, day)
            }
        } catch (nfe: NumberFormatException) {
            println("Couldn't convert date to Calendar when taken out of DB!")

            Calendar.getInstance()
        }
    }
}