package com.example.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Lesson entity representing structured learning units belonging to a Course.
 * Linked to CourseEntity via foreign key with CASCADE deletion.
 */
@Entity(
    tableName = "lessons",
    foreignKeys = [
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["course_id"]),
        Index(value = ["course_id", "order_index"])
    ]
)
data class LessonEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "course_id")
    val courseId: String,

    @ColumnInfo(name = "module_id")
    val moduleId: String,

    @ColumnInfo(name = "module_title")
    val moduleTitle: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "duration")
    val duration: String,

    @ColumnInfo(name = "order_index")
    val orderIndex: Int,

    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "code_example")
    val codeExample: String? = null,

    @ColumnInfo(name = "video_url")
    val videoUrl: String? = null,

    @ColumnInfo(name = "key_takeaways")
    val keyTakeaways: List<String> = emptyList()
)
