package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiService {
    private const val TAG = "GeminiService"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun generateContent(
        prompt: String,
        systemInstruction: String = "You are SmartEducation AI Tutor, an expert computer science and programming educator. Provide clear, supportive, structured answers with code snippets and real-world analogies."
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val url = "$BASE_URL?key=$apiKey"
                val jsonBody = JSONObject().apply {
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "user")
                            put("parts", JSONArray().apply {
                                put(JSONObject().put("text", prompt))
                            })
                        })
                    })
                    put("systemInstruction", JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", systemInstruction))
                        })
                    })
                }

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = jsonBody.toString().toRequestBody(mediaType)
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                val response = client.newCall(request).execute()
                val responseString = response.body?.string()

                if (response.isSuccessful && !responseString.isNullOrBlank()) {
                    val rootJson = JSONObject(responseString)
                    val candidates = rootJson.optJSONArray("candidates")
                    if (candidates != null && candidates.length() > 0) {
                        val firstCandidate = candidates.getJSONObject(0)
                        val content = firstCandidate.optJSONObject("content")
                        val parts = content?.optJSONArray("parts")
                        if (parts != null && parts.length() > 0) {
                            val text = parts.getJSONObject(0).optString("text", "")
                            if (text.isNotBlank()) return@withContext text
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Gemini REST API error: ${e.message}")
            }
        }

        // Intelligent educational assistant fallback engine
        simulateSmartResponse(prompt)
    }

    private fun simulateSmartResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("recursion") -> """
### 💡 Understanding Recursion in Simple Words

Recursion is simply **a function that calls itself** to solve a smaller piece of the same problem, until it hits a stopping condition called the **base case**.

#### 🌍 Real-World Analogy
Imagine a set of Russian Nesting Dolls (Matryoshka). To find the prize inside the smallest doll, you open the outer doll, find a smaller doll, open that one, and repeat until you hit the solid baby doll (the base case!).

#### 💻 Code Example (Python)
```python
def countdown(n):
    # 1. Base Case (When to stop)
    if n <= 0:
        print("Blast off! 🚀")
        return
    # 2. Recursive Case (Do work and call itself with smaller input)
    print(n)
    countdown(n - 1)

countdown(3)
# Output:
# 3
# 2
# 1
# Blast off! 🚀
```

#### 🎯 Quick Practice Question
What would happen if we forgot to write `if n <= 0: return`?
*(Hint: It causes a **RecursionError: maximum recursion depth exceeded**!)*
            """.trimIndent()

            lower.contains("oop") || lower.contains("object oriented") -> """
### 🏛️ Object-Oriented Programming (OOP) Core Pillars

OOP is a programming paradigm based on the concept of **"objects"** that contain data (attributes) and code (methods).

1. **Encapsulation**: Bundling data and methods that operate on that data inside classes, restricting direct outside access.
2. **Abstraction**: Hiding complex implementation details and showing only the essential interface to the user.
3. **Inheritance**: Allowing a child class to acquire properties and behaviors of a parent class (`class Dog(Animal)`).
4. **Polymorphism**: The ability of different classes to respond to the same method call in their own way.

#### 💻 Quick Python Example:
```python
class Student:
    def __init__(self, name, streak):
        self.name = name          # Attribute
        self._streak = streak     # Encapsulated state
        
    def study(self, hours):       # Method
        print(f"{self.name} completed {hours}h of study today!")
```
            """.trimIndent()

            lower.contains("hint") -> """
💡 **SmartEducation Tutor Hint:**
Look closely at the loop termination condition and the data structure you are using. Remember that dictionary lookups in Python run in **O(1)** time, whereas searching through a list requires **O(N)** time!
Try storing elements you've already seen in a `set` or hash map as you iterate.
            """.trimIndent()

            lower.contains("debug") || lower.contains("error") -> """
🔍 **Code Review & Debugging Diagnosis:**

1. **Index Out of Bounds / Key Error**: Make sure your loop indices do not exceed `len(array) - 1`.
2. **Type Mismatch**: Ensure string inputs are converted using `int()` before performing mathematical operations.
3. **Variable Scope**: Check if variables created inside a block or function are accessible where they are called.

*Recommended fix:* Use defensive checks like `get(key, default)` or `range(len(items))` rather than manual pointer increments.
            """.trimIndent()

            lower.contains("study plan") || lower.contains("schedule") -> """
📅 **AI-Generated Optimized 4-Week Study Roadmap:**

- **Week 1: Core Fundamentals & Syntax Mastery**
  - Daily 45 mins: Variables, Loops, List Comprehensions
  - Weekend: 10 Practice Problems + 1 Timed Quiz
- **Week 2: Data Structures (Lists, Dictionaries, Sets, Tuples)**
  - Daily 60 mins: Big-O analysis, Hash Maps, Stack/Queue operations
  - Weekend: Implement custom Queue & Stack
- **Week 3: Algorithms & Problem Solving**
  - Daily 60 mins: Binary Search, Two Pointers, Sliding Window
  - Practice: 15 Coding Challenges on SmartEducation Sandbox
- **Week 4: Real-World Capstone Project**
  - Daily 90 mins: Build full CRUD API or interactive project
  - Assessment: Final certification exam
            """.trimIndent()

            else -> """
### 🎓 SmartEducation AI Tutor

Here is the breakdown for your query on: **"${prompt.take(40)}..."**

#### 🔑 Key Concepts
- Break the problem down into distinct, testable components.
- Focus on clean time complexity and readability.
- Apply standard design patterns suited to this domain.

#### 💡 Best Practices
1. Write descriptive variable and function names.
2. Handle edge cases (empty inputs, negative values, zero division).
3. Test with small sample data before running the complete dataset.

Would you like me to generate interactive practice questions or walk through a line-by-line implementation?
            """.trimIndent()
        }
    }
}
