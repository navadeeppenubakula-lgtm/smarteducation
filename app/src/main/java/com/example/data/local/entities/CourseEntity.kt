package com.example.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Course catalog entity representing curriculum modules.
 * Linked to the instructor (TeacherEntity).
 */
@Entity(
    tableName = "courses",
    foreignKeys = [
        ForeignKey(
            entity = TeacherEntity::class,
            parentColumns = ["id"],
            childColumns = ["instructor_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["instructor_id"]),
        Index(value = ["course_code"], unique = true)
    ]
)
data class CourseEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "course_code")
    val courseCode: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "instructor_id")
    val instructorId: String? = null,

    @ColumnInfo(name = "instructor_name")
    val instructorName: String,

    @ColumnInfo(name = "difficulty")
    val difficulty: String,

    @ColumnInfo(name = "duration")
    val duration: String,

    @ColumnInfo(name = "rating")
    val rating: Float = 4.8f,

    @ColumnInfo(name = "enrolled_count")
    val enrolledCount: Int = 0,

    @ColumnInfo(name = "progress_percent")
    val progressPercent: Int = 0,

    @ColumnInfo(name = "is_published")
    val isPublished: Boolean = true,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
