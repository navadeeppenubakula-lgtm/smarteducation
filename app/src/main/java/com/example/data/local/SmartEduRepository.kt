package com.example.data.local

import com.example.data.local.dao.*
import com.example.data.local.entities.*
import com.example.data.local.relations.*
import kotlinx.coroutines.flow.Flow

/**
 * Clean Repository Pattern wrapper providing unified access to Room DAOs
 * for ViewModels and UI state consumers.
 */
class SmartEduRepository(
    private val userDao: UserDao,
    private val studentDao: StudentDao,
    private val teacherDao: TeacherDao,
    private val courseDao: CourseDao,
    private val lessonDao: LessonDao
) {
    // User operations
    val allUsers: Flow<List<UserEntity>> = userDao.getAllUsers()
    fun getUserById(id: String): Flow<UserEntity?> = userDao.getUserById(id)
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun deleteUser(user: UserEntity) = userDao.deleteUser(user)

    // Student operations
    val allStudents: Flow<List<StudentEntity>> = studentDao.getAllStudents()
    fun getStudentById(id: String): Flow<StudentEntity?> = studentDao.getStudentById(id)
    fun getStudentWithUser(id: String): Flow<StudentWithUser?> = studentDao.getStudentWithUser(id)
    suspend fun insertStudent(student: StudentEntity) = studentDao.insertStudent(student)
    suspend fun updateStudentGamification(studentId: String, streak: Int, xp: Int) =
        studentDao.updateGamification(studentId, streak, xp)

    // Teacher operations
    val allTeachers: Flow<List<TeacherEntity>> = teacherDao.getAllTeachers()
    fun getTeacherById(id: String): Flow<TeacherEntity?> = teacherDao.getTeacherById(id)
    fun getTeacherWithUser(id: String): Flow<TeacherWithUser?> = teacherDao.getTeacherWithUser(id)
    fun getTeacherWithCourses(id: String): Flow<TeacherWithCourses?> = teacherDao.getTeacherWithCourses(id)
    suspend fun insertTeacher(teacher: TeacherEntity) = teacherDao.insertTeacher(teacher)

    // Course operations
    val allCourses: Flow<List<CourseEntity>> = courseDao.getAllCourses()
    fun getCourseById(id: String): Flow<CourseEntity?> = courseDao.getCourseById(id)
    fun getCoursesByCategory(category: String): Flow<List<CourseEntity>> = courseDao.getCoursesByCategory(category)
    fun getCourseWithLessons(courseId: String): Flow<CourseWithLessons?> = courseDao.getCourseWithLessons(courseId)
    suspend fun insertCourse(course: CourseEntity) = courseDao.insertCourse(course)
    suspend fun updateCourseProgress(courseId: String, progress: Int) =
        courseDao.updateCourseProgress(courseId, progress)

    // Lesson operations
    fun getLessonsForCourse(courseId: String): Flow<List<LessonEntity>> =
        lessonDao.getLessonsForCourse(courseId)
    fun getLessonById(id: String): Flow<LessonEntity?> = lessonDao.getLessonById(id)
    suspend fun insertLesson(lesson: LessonEntity) = lessonDao.insertLesson(lesson)
    suspend fun setLessonCompleted(lessonId: String, isCompleted: Boolean) =
        lessonDao.setLessonCompleted(lessonId, isCompleted)

    companion object {
        @Volatile
        private var INSTANCE: SmartEduRepository? = null

        fun getInstance(database: SmartEduDatabase): SmartEduRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = SmartEduRepository(
                    userDao = database.userDao(),
                    studentDao = database.studentDao(),
                    teacherDao = database.teacherDao(),
                    courseDao = database.courseDao(),
                    lessonDao = database.lessonDao()
                )
                INSTANCE = instance
                instance
            }
        }
    }
}
