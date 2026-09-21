package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.*
import com.example.data.local.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Main Room Database for SmartEducation platform.
 * Persists Users, Students, Teachers, Courses, and Lessons.
 */
@Database(
    entities = [
        UserEntity::class,
        StudentEntity::class,
        TeacherEntity::class,
        CourseEntity::class,
        LessonEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SmartEduDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun studentDao(): StudentDao
    abstract fun teacherDao(): TeacherDao
    abstract fun courseDao(): CourseDao
    abstract fun lessonDao(): LessonDao

    companion object {
        private const val DATABASE_NAME = "smart_edu_database.db"

        @Volatile
        private var INSTANCE: SmartEduDatabase? = null

        fun getInstance(context: Context): SmartEduDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartEduDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .addCallback(DatabasePrepopulationCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabasePrepopulationCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        prepopulateInitialData(database)
                    }
                }
            }
        }

        private suspend fun prepopulateInitialData(database: SmartEduDatabase) {
            // Seed Users
            val userStudent = UserEntity(
                id = "usr_student_01",
                email = "alex.morgan@smartedu.io",
                name = "Alex Morgan",
                role = "STUDENT"
            )
            val userTeacher = UserEntity(
                id = "usr_teacher_01",
                email = "priya.sharma@smartedu.io",
                name = "Dr. Priya Sharma",
                role = "TEACHER"
            )
            database.userDao().insertUsers(listOf(userStudent, userTeacher))

            // Seed Student Profile
            val student = StudentEntity(
                id = "std_001",
                userId = "usr_student_01",
                rollNumber = "CSE-2024-042",
                department = "Computer Science & Engineering",
                gradeOrSemester = "Semester 4",
                streakDays = 7,
                xpPoints = 840,
                level = 4,
                rank = "Code Ninja",
                completedCoursesCount = 3,
                learningHours = 48,
                assignmentsDone = 14,
                averageScore = 88,
                attendanceRate = 96.5f
            )
            database.studentDao().insertStudent(student)

            // Seed Teacher Profile
            val teacher = TeacherEntity(
                id = "tch_001",
                userId = "usr_teacher_01",
                employeeId = "FAC-CS-108",
                department = "Computer Science & Engineering",
                specialization = "Python Systems, Cloud & AI Architecture",
                qualification = "Ph.D. in Computer Science (Stanford)",
                rating = 4.9f,
                totalClasses = 4
            )
            database.teacherDao().insertTeacher(teacher)

            // Seed Courses
            val coursePython = CourseEntity(
                id = "course_python",
                courseCode = "CS-PY-101",
                title = "Python Programming & System Design",
                description = "Master modern Python programming from syntax basics to object-oriented architecture, modular development, and real-world backend APIs.",
                category = "Programming",
                instructorId = "tch_001",
                instructorName = "Dr. Priya Sharma",
                difficulty = "Beginner to Advanced",
                duration = "36 Hours",
                rating = 4.9f,
                enrolledCount = 14200,
                progressPercent = 78
            )

            val courseReact = CourseEntity(
                id = "course_react",
                courseCode = "WEB-FS-201",
                title = "Full Stack React & Modern Web Apps",
                description = "Build high-performance web applications with React 19, TypeScript, state machines, Tailwind CSS, and REST/GraphQL APIs.",
                category = "Web Development",
                instructorId = "tch_001",
                instructorName = "Marcus Vance",
                difficulty = "Intermediate",
                duration = "42 Hours",
                rating = 4.8f,
                enrolledCount = 11350,
                progressPercent = 45
            )
            database.courseDao().insertCourses(listOf(coursePython, courseReact))

            // Seed Lessons
            val lessons = listOf(
                LessonEntity(
                    id = "les_1_1",
                    courseId = "course_python",
                    moduleId = "mod_1",
                    moduleTitle = "01 Introduction & Setup",
                    title = "Welcome to Python & Pythonic Thinking",
                    duration = "12 min",
                    orderIndex = 1,
                    isCompleted = true,
                    content = "Python is renowned for its expressive syntax, readable grammar, and immense ecosystem. Learn why top tech companies utilize Python for automation, AI, and scalable backends.",
                    keyTakeaways = listOf("PEP 8 style guide", "Interpreted vs Compiled execution", "Virtual environments")
                ),
                LessonEntity(
                    id = "les_1_2",
                    courseId = "course_python",
                    moduleId = "mod_1",
                    moduleTitle = "01 Introduction & Setup",
                    title = "Variables, Dynamic Typing & Memory Model",
                    duration = "18 min",
                    orderIndex = 2,
                    isCompleted = true,
                    content = "Understand how Python handles variable names as pointers to memory objects, reference counting, and immutable vs mutable primitives.",
                    codeExample = "a = [1, 2, 3]\nb = a\nb.append(4)\nprint(a) # Output: [1, 2, 3, 4] due to shared reference!",
                    keyTakeaways = listOf("Everything is an object", "Id and memory identity", "Pass-by-object-reference")
                ),
                LessonEntity(
                    id = "les_2_1",
                    courseId = "course_python",
                    moduleId = "mod_2",
                    moduleTitle = "02 Control Flow & Iterations",
                    title = "Pattern Matching & Conditional Logic",
                    duration = "22 min",
                    orderIndex = 3,
                    isCompleted = true,
                    content = "Master Python 3.10+ match-case structural pattern matching alongside idiomatic if-elif-else branching.",
                    codeExample = "match command.split():\n    case ['go', direction]:\n        move(direction)\n    case ['quit']:\n        exit()",
                    keyTakeaways = listOf("Structural pattern matching", "Truthiness evaluation", "Ternary expressions")
                )
            )
            database.lessonDao().insertLessons(lessons)
        }
    }
}
