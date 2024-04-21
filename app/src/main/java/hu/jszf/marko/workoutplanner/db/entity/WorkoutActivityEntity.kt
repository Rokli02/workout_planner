package hu.jszf.marko.workoutplanner.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "WorkoutActivityEntity", indices = [
    Index(name = "unique_date", unique = true, value = ["date"])
])
data class WorkoutActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    @ColumnInfo(defaultValue = "CURRENT_DATE")
    val date: Calendar,
    @ColumnInfo(name = "img_id")
    val imgId: Int? = null,
)