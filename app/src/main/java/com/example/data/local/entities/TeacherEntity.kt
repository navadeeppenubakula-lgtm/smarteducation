package com.example.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Teacher / Instructor entity linked to the base UserEntity.
 * Stores faculty credentials, department, and teaching specialization.
 */
@Entity(
    tableName = "teachers",
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
        Index(value = ["employee_id"], unique = true)
    ]
)
data class TeacherEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "employee_id")
    val employeeId: String,

    @ColumnInfo(name = "department")
    val department: String = "Computer Science & Engineering",

    @ColumnInfo(name = "specialization")
    val specialization: String = "Software Architecture & AI",

    @ColumnInfo(name = "qualification")
    val qualification: String = "Ph.D. in Computer Science",

    @ColumnInfo(name = "bio")
    val bio: String? = null,

    @ColumnInfo(name = "office_hours")
    val officeHours: String? = null,

    @ColumnInfo(name = "rating")
    val rating: Float = 4.9f,

    @ColumnInfo(name = "total_classes")
    val totalClasses: Int = 1
)
