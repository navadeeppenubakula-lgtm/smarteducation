package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleRepository
import com.example.data.UserRole
import com.example.ui.components.SmartEduTopBar
import com.example.ui.screens.*
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.BrandTertiary
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

sealed class Screen {
    object Home : Screen()
    object Courses : Screen()
    data class CoursePlayer(val courseId: String) : Screen()
    object AiTutor : Screen()
    object Practice : Screen()
    object Assessment : Screen()
    object SkillGap : Screen()
    object StudyPlan : Screen()
    object Projects : Screen()
    object TeacherHub : Screen()
    object Profile : Screen()
    object Notifications : Screen()
    object PortalHub : Screen()
}

@Composable
fun SmartEduApp() {
    val currentUser by SampleRepository.currentUser.collectAsState()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    // When role changes, if switched to Teacher, we can show TeacherHub or notify
    var activeRole by remember { mutableStateOf(currentUser.role) }

    Scaffold(
        topBar = {
            // Only show main top bar on primary pages
            if (currentScreen !is Screen.CoursePlayer) {
                SmartEduTopBar(
                    currentRole = activeRole,
                    streakDays = currentUser.streakDays,
                    notificationCount = SampleRepository.notifications.size,
                    onRoleChange = { newRole ->
                        activeRole = newRole
                        SampleRepository.currentUser.value = currentUser.copy(role = newRole)
                        if (newRole == UserRole.TEACHER) {
                            currentScreen = Screen.TeacherHub
                        } else if (newRole == UserRole.STUDENT) {
                            currentScreen = Screen.Home
                        }
                    },
                    onNotificationsClick = { currentScreen = Screen.Notifications },
                    onProfileClick = { currentScreen = Screen.Profile }
                )
            }
        },
        bottomBar = {
            if (currentScreen !is Screen.CoursePlayer) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    NavigationBarItem(
                        selected = currentScreen is Screen.Home,
                        onClick = { currentScreen = Screen.Home },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BrandPrimary,
                            indicatorColor = BrandPrimary.copy(alpha = 0.12f)
                        )
                    )
                    NavigationBarItem(
                        selected = currentScreen is Screen.Courses,
                        onClick = { currentScreen = Screen.Courses },
                        icon = { Icon(Icons.Default.School, contentDescription = "Courses") },
                        label = { Text("Courses") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BrandPrimary,
                            indicatorColor = BrandPrimary.copy(alpha = 0.12f)
                        )
                    )
                    NavigationBarItem(
                        selected = currentScreen is Screen.AiTutor,
                        onClick = { currentScreen = Screen.AiTutor },
                        icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "AI Tutor") },
                        label = { Text("AI Tutor") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BrandSecondary,
                            indicatorColor = BrandSecondary.copy(alpha = 0.12f)
                        )
                    )
                    NavigationBarItem(
                        selected = currentScreen is Screen.Practice,
                        onClick = { currentScreen = Screen.Practice },
                        icon = { Icon(Icons.Default.Code, contentDescription = "Practice") },
                        label = { Text("Practice") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BrandPrimary,
                            indicatorColor = BrandPrimary.copy(alpha = 0.12f)
                        )
                    )
                    NavigationBarItem(
                        selected = currentScreen is Screen.PortalHub ||
                                currentScreen is Screen.Assessment ||
                                currentScreen is Screen.SkillGap ||
                                currentScreen is Screen.StudyPlan ||
                                currentScreen is Screen.Projects ||
                                currentScreen is Screen.TeacherHub ||
                                currentScreen is Screen.Profile ||
                                currentScreen is Screen.Notifications,
                        onClick = { currentScreen = Screen.PortalHub },
                        icon = { Icon(Icons.Default.GridView, contentDescription = "Portal Tools") },
                        label = { Text("Tools") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BrandTertiary,
                            indicatorColor = BrandTertiary.copy(alpha = 0.12f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        user = currentUser,
                        onNavigateToCourse = { courseId -> currentScreen = Screen.CoursePlayer(courseId) },
                        onNavigateToAiTutor = { currentScreen = Screen.AiTutor },
                        onNavigateToCoding = { currentScreen = Screen.Practice },
                        onNavigateToAssessment = { currentScreen = Screen.Assessment },
                        onNavigateToStudyPlan = { currentScreen = Screen.StudyPlan },
                        onNavigateToSkillGap = { currentScreen = Screen.SkillGap }
                    )
                }

                is Screen.Courses -> {
                    CoursesScreen(
                        onCourseClick = { courseId -> currentScreen = Screen.CoursePlayer(courseId) }
                    )
                }

                is Screen.CoursePlayer -> {
                    CoursePlayerScreen(
                        courseId = screen.courseId,
                        onBackClick = { currentScreen = Screen.Courses }
                    )
                }

                is Screen.AiTutor -> {
                    AiTutorScreen()
                }

                is Screen.Practice -> {
                    CodingPracticeScreen()
                }

                is Screen.Assessment -> {
                    AssessmentScreen(onNavigateBack = { currentScreen = Screen.PortalHub })
                }

                is Screen.SkillGap -> {
                    SkillGapScreen(onNavigateToCourse = { courseId -> currentScreen = Screen.CoursePlayer(courseId) })
                }

                is Screen.StudyPlan -> {
                    StudyPlannerScreen()
                }

                is Screen.Projects -> {
                    ProjectsScreen()
                }

                is Screen.TeacherHub -> {
                    TeacherDashboardScreen()
                }

                is Screen.Profile -> {
                    ProfileAndCertificatesScreen(user = currentUser)
                }

                is Screen.Notifications -> {
                    NotificationsScreen(onNavigateBack = { currentScreen = Screen.PortalHub })
                }

                is Screen.PortalHub -> {
                    PortalHubScreen(
                        onNavigateTo = { dest -> currentScreen = dest }
                    )
                }
            }
        }
    }
}

data class PortalToolItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val destination: Screen
)

@Composable
fun PortalHubScreen(
    onNavigateTo: (Screen) -> Unit
) {
    val tools = listOf(
        PortalToolItem(
            title = "AI Assessments",
            description = "Adaptive timed quizzes with instant diagnostics",
            icon = Icons.Default.Quiz,
            color = SuccessGreen,
            destination = Screen.Assessment
        ),
        PortalToolItem(
            title = "Skill Gap Analyzer",
            description = "Analyze job benchmarks vs current proficiency",
            icon = Icons.Default.CompareArrows,
            color = BrandPrimary,
            destination = Screen.SkillGap
        ),
        PortalToolItem(
            title = "AI Study Planner",
            description = "Dynamic multi-week study schedules & goals",
            icon = Icons.Default.CalendarMonth,
            color = BrandSecondary,
            destination = Screen.StudyPlan
        ),
        PortalToolItem(
            title = "Capstone Projects",
            description = "Build real apps evaluated by AI rubrics",
            icon = Icons.Default.RocketLaunch,
            color = BrandTertiary,
            destination = Screen.Projects
        ),
        PortalToolItem(
            title = "Teacher & Classroom",
            description = "Attendance tracker & AI teaching assistant",
            icon = Icons.Default.CoPresent,
            color = WarningAmber,
            destination = Screen.TeacherHub
        ),
        PortalToolItem(
            title = "Certificates & Badges",
            description = "Download verified credentials & view XP",
            icon = Icons.Default.WorkspacePremium,
            color = Color(0xFFEAB308),
            destination = Screen.Profile
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "SmartEducation Hub",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Complete suite of AI-powered educational tools",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(tools) { tool ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigateTo(tool.destination) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(14.dp)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(tool.color.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(tool.icon, contentDescription = null, tint = tool.color, modifier = Modifier.size(22.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = tool.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tool.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 16.sp,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}
