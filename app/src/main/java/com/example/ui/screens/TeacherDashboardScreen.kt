package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AttendanceStatus
import com.example.data.GeminiService
import com.example.data.SampleRepository
import com.example.data.StudentAttendance
import com.example.ui.components.SmartEduStatCard
import com.example.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun TeacherDashboardScreen() {
    val coroutineScope = rememberCoroutineScope()
    var students by remember { mutableStateOf(SampleRepository.studentList) }

    var teacherPrompt by remember { mutableStateOf("Create a 3-question MCQ quiz on Python OOP with answer key") }
    var aiGeneratedContent by remember { mutableStateOf<String?>(null) }
    var isGeneratingQuiz by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Instructor & Classroom Hub",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Classroom CSE-2024 • Section A • 48 Students",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Metrics Overview
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SmartEduStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Attendance",
                    value = "92%",
                    subtitle = "Today",
                    icon = Icons.Default.CoPresent,
                    iconTint = SuccessGreen
                )
                SmartEduStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Class Avg",
                    value = "84%",
                    subtitle = "Quizzes",
                    icon = Icons.Default.TrendingUp,
                    iconTint = BrandPrimary
                )
                SmartEduStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Pending",
                    value = "6",
                    subtitle = "Submissions",
                    icon = Icons.Default.Assignment,
                    iconTint = WarningAmber
                )
            }
        }

        // Attendance Management Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Daily Attendance Tracker",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Live Sync",
                            style = MaterialTheme.typography.labelSmall,
                            color = SuccessGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    students.forEachIndexed { index, student ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(student.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                Text("${student.rollNo} • Rate: ${student.attendanceRate}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            // Status switcher buttons
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                FilterChip(
                                    selected = student.status == AttendanceStatus.PRESENT,
                                    onClick = {
                                        students = students.toMutableList().also {
                                            it[index] = student.copy(status = AttendanceStatus.PRESENT)
                                        }
                                    },
                                    label = { Text("P", style = MaterialTheme.typography.labelSmall) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SuccessGreen,
                                        selectedLabelColor = Color.White
                                    )
                                )
                                FilterChip(
                                    selected = student.status == AttendanceStatus.LATE,
                                    onClick = {
                                        students = students.toMutableList().also {
                                            it[index] = student.copy(status = AttendanceStatus.LATE)
                                        }
                                    },
                                    label = { Text("L", style = MaterialTheme.typography.labelSmall) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = WarningAmber,
                                        selectedLabelColor = Color.White
                                    )
                                )
                                FilterChip(
                                    selected = student.status == AttendanceStatus.ABSENT,
                                    onClick = {
                                        students = students.toMutableList().also {
                                            it[index] = student.copy(status = AttendanceStatus.ABSENT)
                                        }
                                    },
                                    label = { Text("A", style = MaterialTheme.typography.labelSmall) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = ErrorRed,
                                        selectedLabelColor = Color.White
                                    )
                                )
                            }
                        }
                        if (index < students.size - 1) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                        }
                    }
                }
            }
        }

        // AI Teaching Assistant Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, BrandPrimary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = BrandPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI Teaching Assistant",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Instantly generate quizzes, coding tasks, or lesson summaries for your students",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = teacherPrompt,
                        onValueChange = { teacherPrompt = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Quiz / Lesson Prompt") },
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isGeneratingQuiz = true
                                aiGeneratedContent = GeminiService.generateContent(
                                    "As a university computer science teacher assistant, please: $teacherPrompt"
                                )
                                isGeneratingQuiz = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isGeneratingQuiz) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating Content...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Classroom Material")
                        }
                    }

                    if (aiGeneratedContent != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Generated Classroom Content:",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = aiGeneratedContent ?: "",
                                    style = MaterialTheme.typography.bodySmall,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
