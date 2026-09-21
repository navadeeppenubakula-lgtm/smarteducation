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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DailyStudyTask
import com.example.data.GeminiService
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.SuccessGreen
import kotlinx.coroutines.launch

@Composable
fun StudyPlannerScreen() {
    val coroutineScope = rememberCoroutineScope()

    var selectedGoal by remember { mutableStateOf("Full-Stack Web & Python Mastery") }
    var hoursPerDay by remember { mutableIntStateOf(2) }
    var isGeneratingPlan by remember { mutableStateOf(false) }

    val goals = listOf(
        "Full-Stack Web & Python Mastery",
        "Data Structures & Alg Interview Prep",
        "Machine Learning & AI Foundations"
    )

    var weeksPlan by remember {
        mutableStateOf(
            listOf(
                DailyStudyTask("w1_1", "Monday", "09:00 AM", "Python Foundations & Memory Model", "Lesson", 45, isCompleted = true),
                DailyStudyTask("w1_2", "Tuesday", "11:00 AM", "List Comprehensions & Generator Expressions", "Practice", 60, isCompleted = true),
                DailyStudyTask("w1_3", "Wednesday", "03:00 PM", "OOP Classes, Dunder Methods & Inheritance", "Lesson", 50, isCompleted = false),
                DailyStudyTask("w1_4", "Thursday", "05:00 PM", "Hash Maps (Dicts) & Amortized O(1) Lookups", "Practice", 60, isCompleted = false),
                DailyStudyTask("w1_5", "Friday", "02:00 PM", "SQL Joins & Relational Schema Design", "Quiz", 30, isCompleted = false),
                DailyStudyTask("w1_6", "Saturday", "10:00 AM", "Capstone Project Milestone: API Integration", "Project", 90, isCompleted = false)
            )
        )
    }

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
                    text = "AI Study Planner",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Dynamic AI-optimized schedule tuned to your target goals and pace",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Configuration Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Target Goal:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    goals.forEach { goal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedGoal == goal,
                                onClick = { selectedGoal = goal },
                                colors = RadioButtonDefaults.colors(selectedColor = BrandPrimary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(goal, style = MaterialTheme.typography.bodyMedium)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Commitment: $hoursPerDay hours / day",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Slider(
                        value = hoursPerDay.toFloat(),
                        onValueChange = { hoursPerDay = it.toInt() },
                        valueRange = 1f..6f,
                        steps = 4,
                        colors = SliderDefaults.colors(thumbColor = BrandPrimary, activeTrackColor = BrandPrimary)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                isGeneratingPlan = true
                                // Call GeminiService
                                GeminiService.generateContent(
                                    "Generate an optimized study plan for $selectedGoal with $hoursPerDay hours per day."
                                )
                                isGeneratingPlan = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isGeneratingPlan) {
                            CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating Personalized Plan...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Regenerate AI Schedule")
                        }
                    }
                }
            }
        }

        // Timeline Schedule Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Weekly Schedule: Week 1 of 4",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                val completedCount = weeksPlan.count { it.isCompleted }
                Text(
                    text = "$completedCount/${weeksPlan.size} Done",
                    style = MaterialTheme.typography.labelMedium,
                    color = BrandPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Daily Tasks List
        itemsIndexed(weeksPlan) { index, task ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = { checked ->
                            weeksPlan = weeksPlan.toMutableList().also { list ->
                                list[index] = task.copy(isCompleted = checked)
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = BrandPrimary)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${task.dayName} • ${task.timeSlot}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "${task.estimatedMinutes} min",
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = task.topic,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.SemiBold,
                            color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "Activity: ${task.type}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
