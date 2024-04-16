package hu.jszf.marko.workoutplanner.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ExerciseEntity")
data class ExerciseEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val name: String,
    val description: String,
    @ColumnInfo(name = "img_id")
    val imgId: Int? = null,
)