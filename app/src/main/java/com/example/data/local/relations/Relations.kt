package com.example.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data.local.entities.*

/**
 * 1-to-1 Relation between Student and base User profile.
 */
data class StudentWithUser(
    @Embedded val student: StudentEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "id"
    )
    val user: UserEntity
)

/**
 * 1-to-1 Relation between Teacher and base User profile.
 */
data class TeacherWithUser(
    @Embedded val teacher: TeacherEntity,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "id"
    )
    val user: UserEntity
)

/**
 * 1-to-Many Relation between Course and its Lessons.
 */
data class CourseWithLessons(
    @Embedded val course: CourseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "course_id"
    )
    val lessons: List<LessonEntity>
)

/**
 * 1-to-Many Relation between Teacher and authored Courses.
 */
data class TeacherWithCourses(
    @Embedded val teacher: TeacherEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "instructor_id"
    )
    val courses: List<CourseEntity>
)
