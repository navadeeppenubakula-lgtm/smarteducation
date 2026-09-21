package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatMessage
import com.example.data.GeminiService
import com.example.ui.theme.BrandPrimary
import com.example.ui.theme.BrandSecondary
import com.example.ui.theme.BrandTertiary
import kotlinx.coroutines.launch

@Composable
fun AiTutorScreen() {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var inputPrompt by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                id = "init_1",
                isUser = false,
                text = "Hello Alex! I am your **SmartEducation AI Tutor**.\n\nI can explain concepts simply, generate practice questions, debug your code, create study flashcards, or break down complex algorithms. What would you like to explore today?",
                followUpPrompts = listOf(
                    "Explain recursion in simple words",
                    "How does Python memory management work?",
                    "What are SQL Joins with diagrams?",
                    "Explain OOP like I'm 10"
                )
            )
        )
    }

    val quickActionPrompts = listOf(
        "Explain simply",
        "Explain like I'm 10",
        "Give code examples",
        "Generate practice quiz",
        "Debug my code",
        "Create flashcards"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // AI Tutor Header
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(BrandPrimary, BrandTertiary))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Tutor",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "SmartEducation AI Tutor",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isGenerating) "Thinking..." else "Always Online • Computer Science Expert",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isGenerating) BrandSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                IconButton(
                    onClick = {
                        messages.clear()
                        messages.add(
                            ChatMessage(
                                id = "new_chat",
                                isUser = false,
                                text = "New chat started! What subject or problem would you like to focus on?",
                                followUpPrompts = listOf(
                                    "Explain Dijkstra's Algorithm",
                                    "Explain React useEffect lifecycle",
                                    "Generate Python OOP practice questions"
                                )
                            )
                        )
                    }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "New Chat", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        // Chat Message List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(messages) { message ->
                if (message.isUser) {
                    // User Message
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp),
                            color = BrandPrimary,
                            modifier = Modifier.widthIn(max = 280.dp)
                        ) {
                            Text(
                                text = message.text,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                } else {
                    // AI Tutor Message Card
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            modifier = Modifier.widthIn(max = 340.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 6.dp)
                                ) {
                                    Icon(
                                        Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = BrandSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "AI Tutor",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = BrandSecondary
                                    )
                                }

                                Text(
                                    text = message.text,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 22.sp
                                )

                                // Action Buttons below AI response
                                Spacer(modifier = Modifier.height(10.dp))
                                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                                Spacer(modifier = Modifier.height(6.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TextButton(
                                        onClick = {
                                            sendMessage(
                                                prompt = "Can you explain that more deeply with a real-world scenario?",
                                                messages = messages,
                                                coroutineScope = coroutineScope,
                                                onStatusChange = { isGenerating = it }
                                            )
                                        },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text("Explain More", style = MaterialTheme.typography.labelSmall)
                                    }

                                    TextButton(
                                        onClick = {
                                            sendMessage(
                                                prompt = "Give me 2 practical code examples showing this in action.",
                                                messages = messages,
                                                coroutineScope = coroutineScope,
                                                onStatusChange = { isGenerating = it }
                                            )
                                        },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text("Give Example", style = MaterialTheme.typography.labelSmall)
                                    }

                                    TextButton(
                                        onClick = {
                                            sendMessage(
                                                prompt = "Generate a practice question for me on this topic.",
                                                messages = messages,
                                                coroutineScope = coroutineScope,
                                                onStatusChange = { isGenerating = it }
                                            )
                                        },
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text("Practice", style = MaterialTheme.typography.labelSmall)
                                    }
                                }

                                // Follow up suggestions
                                if (message.followUpPrompts.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Suggested queries:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    message.followUpPrompts.forEach { prompt ->
                                        SuggestionChip(
                                            onClick = {
                                                sendMessage(
                                                    prompt = prompt,
                                                    messages = messages,
                                                    coroutineScope = coroutineScope,
                                                    onStatusChange = { isGenerating = it }
                                                )
                                            },
                                            label = { Text(prompt, style = MaterialTheme.typography.labelSmall) },
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isGenerating) {
                item {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "AI Tutor is writing explanation...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Quick Action Chips Row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickActionPrompts) { prompt ->
                AssistChip(
                    onClick = {
                        inputPrompt = if (inputPrompt.isBlank()) "$prompt: " else "$inputPrompt ($prompt)"
                    },
                    label = { Text(prompt, style = MaterialTheme.typography.labelSmall) },
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        // Message Input Field
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputPrompt,
                    onValueChange = { inputPrompt = it },
                    placeholder = { Text("Ask anything: 'Explain recursion simply'...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandPrimary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (inputPrompt.isNotBlank() && !isGenerating) {
                            val userText = inputPrompt.trim()
                            inputPrompt = ""
                            sendMessage(
                                prompt = userText,
                                messages = messages,
                                coroutineScope = coroutineScope,
                                onStatusChange = { isGenerating = it }
                            )
                        }
                    },
                    enabled = inputPrompt.isNotBlank() && !isGenerating,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = BrandPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.size(46.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = if (inputPrompt.isNotBlank()) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun sendMessage(
    prompt: String,
    messages: MutableList<ChatMessage>,
    coroutineScope: kotlinx.coroutines.CoroutineScope,
    onStatusChange: (Boolean) -> Unit
) {
    messages.add(ChatMessage(id = System.currentTimeMillis().toString(), isUser = true, text = prompt))
    onStatusChange(true)

    coroutineScope.launch {
        val answer = GeminiService.generateContent(prompt)
        messages.add(
            ChatMessage(
                id = (System.currentTimeMillis() + 1).toString(),
                isUser = false,
                text = answer,
                followUpPrompts = listOf(
                    "Can you test my knowledge on this?",
                    "Show how to implement this in Python"
                )
            )
        )
        onStatusChange(false)
    }
}
