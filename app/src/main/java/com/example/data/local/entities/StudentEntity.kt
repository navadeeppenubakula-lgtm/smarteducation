package com.example.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Student profile entity linked to the base UserEntity.
 * Stores academic metrics, gamification statistics, and attendance records.
 */
@Entity(
    tableName = "students",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"], unique = true),
        Index(value = ["roll_number"], unique = true)
    ]
)
data class StudentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "roll_number")
    val rollNumber: String,

    @ColumnInfo(name = "department")
    val department: String = "Computer Science",

    @ColumnInfo(name = "grade_or_semester")
    val gradeOrSemester: String = "Semester 4",

    @ColumnInfo(name = "streak_days")
    val streakDays: Int = 0,

    @ColumnInfo(name = "xp_points")
    val xpPoints: Int = 0,

    @ColumnInfo(name = "level")
    val level: Int = 1,

    @ColumnInfo(name = "rank")
    val rank: String = "Novice",

    @ColumnInfo(name = "completed_courses_count")
    val completedCoursesCount: Int = 0,

    @ColumnInfo(name = "learning_hours")
    val learningHours: Int = 0,

    @ColumnInfo(name = "assignments_done")
    val assignmentsDone: Int = 0,

    @ColumnInfo(name = "average_score")
    val averageScore: Int = 0,

    @ColumnInfo(name = "attendance_rate")
    val attendanceRate: Float = 100.0f
)
