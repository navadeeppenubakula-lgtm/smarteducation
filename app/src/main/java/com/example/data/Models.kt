package com.example.data

enum class UserRole {
    STUDENT, TEACHER, ADMIN
}

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val streakDays: Int,
    val xpPoints: Int,
    val level: Int,
    val rank: String,
    val completedCourses: Int,
    val learningHours: Int,
    val assignmentsDone: Int,
    val averageScore: Int
)

data class Lesson(
    val id: String,
    val title: String,
    val duration: String,
    val isCompleted: Boolean = false,
    val content: String,
    val codeExample: String? = null,
    val keyTakeaways: List<String> = emptyList()
)

data class CourseModule(
    val id: String,
    val order: Int,
    val title: String,
    val lessons: List<Lesson>
)

data class Course(
    val id: String,
    val title: String,
    val category: String,
    val instructor: String,
    val difficulty: String,
    val duration: String,
    val rating: Float,
    val enrolledCount: Int,
    val progress: Int,
    val description: String,
    val modules: List<CourseModule>
)

data class ChatMessage(
    val id: String,
    val isUser: Boolean,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val codeSnippet: String? = null,
    val followUpPrompts: List<String> = emptyList()
)

data class AssessmentQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val topic: String,
    val difficulty: String
)

data class AssessmentResult(
    val score: Int,
    val totalQuestions: Int,
    val accuracy: Float,
    val timeTakenSeconds: Int,
    val strongTopics: List<String>,
    val weakTopics: List<String>,
    val aiExplanation: String,
    val recommendedTopics: List<String>
)

data class TestCase(
    val input: String,
    val expectedOutput: String
)

data class CodingChallenge(
    val id: String,
    val title: String,
    val difficulty: String,
    val tags: List<String>,
    val description: String,
    val starterCode: Map<String, String>,
    val testCases: List<TestCase>,
    val solutionExplanation: String,
    val hints: List<String>
)

data class SkillItem(
    val name: String,
    val currentPercent: Int,
    val targetPercent: Int,
    val category: String,
    val status: String
)

data class DailyStudyTask(
    val id: String,
    val dayName: String,
    val timeSlot: String,
    val topic: String,
    val type: String, // "Lesson", "Practice", "Quiz", "Project"
    val estimatedMinutes: Int,
    var isCompleted: Boolean = false
)

data class StudyPlanWeek(
    val weekNumber: Int,
    val title: String,
    val focusGoal: String,
    val dailyTasks: List<DailyStudyTask>
)

data class ProjectItem(
    val id: String,
    val title: String,
    val level: String, // "Beginner", "Intermediate", "Advanced"
    val techStack: List<String>,
    val description: String,
    val milestones: List<String>,
    val status: String, // "Not Started", "In Progress", "Submitted", "Evaluated"
    val feedback: String? = null,
    val rubricScore: Int? = null
)

enum class AttendanceStatus {
    PRESENT, ABSENT, LATE
}

data class StudentAttendance(
    val studentId: String,
    val name: String,
    val rollNo: String,
    var status: AttendanceStatus,
    val streak: Int,
    val attendanceRate: Int
)

data class BadgeItem(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val unlocked: Boolean,
    val unlockedDate: String? = null
)

data class CertificateItem(
    val id: String,
    val studentName: String,
    val courseName: String,
    val completionDate: String,
    val instructor: String,
    val credentialId: String,
    val gradeScore: String
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val type: String, // "assignment", "quiz", "recommendation", "announcement"
    val isRead: Boolean = false
)

data class AssignmentItem(
    val id: String,
    val title: String,
    val courseName: String,
    val dueDate: String,
    val status: String, // "Pending", "Submitted", "Graded", "Overdue"
    val score: String? = null,
    val maxScore: Int = 100
)
