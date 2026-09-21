package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entities.*
import com.example.data.local.relations.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    fun getUserById(id: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUserById(id: String)
}

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    fun getStudentById(id: String): Flow<StudentEntity?>

    @Query("SELECT * FROM students WHERE user_id = :userId LIMIT 1")
    fun getStudentByUserId(userId: String): Flow<StudentEntity?>

    @Transaction
    @Query("SELECT * FROM students WHERE id = :id LIMIT 1")
    fun getStudentWithUser(id: String): Flow<StudentWithUser?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudents(students: List<StudentEntity>)

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Query("UPDATE students SET streak_days = :streak, xp_points = :xp WHERE id = :studentId")
    suspend fun updateGamification(studentId: String, streak: Int, xp: Int)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)
}

@Dao
interface TeacherDao {
    @Query("SELECT * FROM teachers")
    fun getAllTeachers(): Flow<List<TeacherEntity>>

    @Query("SELECT * FROM teachers WHERE id = :id LIMIT 1")
    fun getTeacherById(id: String): Flow<TeacherEntity?>

    @Transaction
    @Query("SELECT * FROM teachers WHERE id = :id LIMIT 1")
    fun getTeacherWithUser(id: String): Flow<TeacherWithUser?>

    @Transaction
    @Query("SELECT * FROM teachers WHERE id = :id LIMIT 1")
    fun getTeacherWithCourses(id: String): Flow<TeacherWithCourses?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacher(teacher: TeacherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeachers(teachers: List<TeacherEntity>)

    @Update
    suspend fun updateTeacher(teacher: TeacherEntity)

    @Delete
    suspend fun deleteTeacher(teacher: TeacherEntity)
}

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY created_at DESC")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :id LIMIT 1")
    fun getCourseById(id: String): Flow<CourseEntity?>

    @Query("SELECT * FROM courses WHERE category = :category ORDER BY rating DESC")
    fun getCoursesByCategory(category: String): Flow<List<CourseEntity>>

    @Transaction
    @Query("SELECT * FROM courses WHERE id = :courseId LIMIT 1")
    fun getCourseWithLessons(courseId: String): Flow<CourseWithLessons?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: CourseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<CourseEntity>)

    @Update
    suspend fun updateCourse(course: CourseEntity)

    @Query("UPDATE courses SET progress_percent = :progress WHERE id = :courseId")
    suspend fun updateCourseProgress(courseId: String, progress: Int)

    @Delete
    suspend fun deleteCourse(course: CourseEntity)
}

@Dao
interface LessonDao {
    @Query("SELECT * FROM lessons WHERE course_id = :courseId ORDER BY order_index ASC")
    fun getLessonsForCourse(courseId: String): Flow<List<LessonEntity>>

    @Query("SELECT * FROM lessons WHERE id = :id LIMIT 1")
    fun getLessonById(id: String): Flow<LessonEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: LessonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<LessonEntity>)

    @Update
    suspend fun updateLesson(lesson: LessonEntity)

    @Query("UPDATE lessons SET is_completed = :isCompleted WHERE id = :lessonId")
    suspend fun setLessonCompleted(lessonId: String, isCompleted: Boolean)

    @Delete
    suspend fun deleteLesson(lesson: LessonEntity)

    @Query("DELETE FROM lessons WHERE course_id = :courseId")
    suspend fun deleteLessonsForCourse(courseId: String)
}
