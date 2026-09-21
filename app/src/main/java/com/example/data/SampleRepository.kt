package com.example.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SampleRepository {

    val currentUser = MutableStateFlow(
        UserProfile(
            id = "std_001",
            name = "Alex Morgan",
            email = "alex.morgan@smartedu.io",
            role = UserRole.STUDENT,
            streakDays = 7,
            xpPoints = 840,
            level = 4,
            rank = "Code Ninja",
            completedCourses = 3,
            learningHours = 48,
            assignmentsDone = 14,
            averageScore = 88
        )
    )

    val courses = MutableStateFlow(
        listOf(
            Course(
                id = "course_python",
                title = "Python Programming & System Design",
                category = "Programming",
                instructor = "Dr. Priya Sharma",
                difficulty = "Beginner to Advanced",
                duration = "36 Hours",
                rating = 4.9f,
                enrolledCount = 14200,
                progress = 78,
                description = "Master modern Python programming from syntax basics to object-oriented architecture, modular development, and real-world backend APIs.",
                modules = listOf(
                    CourseModule(
                        id = "mod_1",
                        order = 1,
                        title = "01 Introduction & Setup",
                        lessons = listOf(
                            Lesson(
                                id = "les_1_1",
                                title = "Welcome to Python & Pythonic Thinking",
                                duration = "12 min",
                                isCompleted = true,
                                content = "Python is renowned for its expressive syntax, readable grammar, and immense ecosystem. Learn why top tech companies utilize Python for automation, AI, and scalable backends.",
                                keyTakeaways = listOf("PEP 8 style guide", "Interpreted vs Compiled execution", "Virtual environments")
                            ),
                            Lesson(
                                id = "les_1_2",
                                title = "Variables, Dynamic Typing & Memory Model",
                                duration = "18 min",
                                isCompleted = true,
                                content = "Understand how Python handles variable names as pointers to memory objects, reference counting, and immutable vs mutable primitives.",
                                codeExample = "a = [1, 2, 3]\nb = a\nb.append(4)\nprint(a) # Output: [1, 2, 3, 4] due to shared reference!",
                                keyTakeaways = listOf("Everything is an object", "Id and memory identity", "Pass-by-object-reference")
                            )
                        )
                    ),
                    CourseModule(
                        id = "mod_2",
                        order = 2,
                        title = "02 Control Flow & Loops",
                        lessons = listOf(
                            Lesson(
                                id = "les_2_1",
                                title = "Conditional Logic & Pattern Matching",
                                duration = "15 min",
                                isCompleted = true,
                                content = "Explore chained if-elif-else blocks, ternary operators, and Python 3.10+ structural match-case syntax for clean branching.",
                                codeExample = "status_code = 404\nmatch status_code:\n    case 200: print('OK')\n    case 404: print('Not Found')\n    case _: print('Unknown')",
                                keyTakeaways = listOf("Short-circuit evaluation", "Truthiness in Python", "Structural pattern matching")
                            ),
                            Lesson(
                                id = "les_2_2",
                                title = "For Loops, Iterators & List Comprehensions",
                                duration = "22 min",
                                isCompleted = true,
                                content = "Harness list comprehensions, generator expressions, and the iter/next protocol for blazing fast memory-efficient iterations.",
                                codeExample = "squares = [x**2 for x in range(10) if x % 2 == 0]\nprint(squares) # [0, 4, 16, 36, 64]",
                                keyTakeaways = listOf("List comprehensions vs map()", "Generator expressions save memory", "Enumerate & zip helpers")
                            )
                        )
                    ),
                    CourseModule(
                        id = "mod_3",
                        order = 3,
                        title = "03 Functions & Scope",
                        lessons = listOf(
                            Lesson(
                                id = "les_3_1",
                                title = "First-Class Functions, *args, and **kwargs",
                                duration = "25 min",
                                isCompleted = true,
                                content = "Functions in Python can be assigned to variables, passed into other functions, and returned from functions dynamically.",
                                codeExample = "def logger(fn):\n    def wrapper(*args, **kwargs):\n        print(f'Calling {fn.__name__}')\n        return fn(*args, **kwargs)\n    return wrapper",
                                keyTakeaways = listOf("Variadic parameters", "Lexical scoping (LEGB rule)", "Decorators pattern")
                            )
                        )
                    ),
                    CourseModule(
                        id = "mod_4",
                        order = 4,
                        title = "04 Object-Oriented Programming (OOP)",
                        lessons = listOf(
                            Lesson(
                                id = "les_4_1",
                                title = "Classes, Dunder Methods & Encapsulation",
                                duration = "30 min",
                                isCompleted = false,
                                content = "Build robust domain entities using class blueprints, __init__, __str__, __repr__, and private name mangling with double underscores.",
                                codeExample = "class BankAccount:\n    def __init__(self, owner, balance=0):\n        self.owner = owner\n        self.__balance = balance\n\n    def deposit(self, amt):\n        if amt > 0:\n            self.__balance += amt",
                                keyTakeaways = listOf("Dunder protocols", "Properties with @property", "Class vs Instance variables")
                            )
                        )
                    ),
                    CourseModule(
                        id = "mod_5",
                        order = 5,
                        title = "05 Data Structures & Algorithms",
                        lessons = listOf(
                            Lesson(
                                id = "les_5_1",
                                title = "Hash Maps (Dicts) & Time Complexity",
                                duration = "28 min",
                                isCompleted = false,
                                content = "Analyze internal hash tables, collision resolution, and why dictionary lookups are amortized O(1) in CPython.",
                                keyTakeaways = listOf("Hash function criteria", "Amortized O(1) lookup", "Set union and intersections")
                            )
                        )
                    )
                )
            ),
            Course(
                id = "course_react",
                title = "Full-Stack Web Dev with React & TypeScript",
                category = "Web Development",
                instructor = "Marcus Chen",
                difficulty = "Intermediate",
                duration = "42 Hours",
                rating = 4.8f,
                enrolledCount = 9800,
                progress = 52,
                description = "Build full-featured production web applications using React 19, TypeScript, state management, Server Components, and RESTful APIs.",
                modules = listOf(
                    CourseModule(
                        id = "react_mod_1",
                        order = 1,
                        title = "01 Modern React Principles",
                        lessons = listOf(
                            Lesson(
                                id = "react_1_1",
                                title = "Virtual DOM, JSX & Reactive Rendering",
                                duration = "20 min",
                                isCompleted = true,
                                content = "Understand reconciliation, fiber architecture, and unidirectional data flow in modern React applications.",
                                keyTakeaways = listOf("Reconciliation engine", "Pure render functions", "JSX compilation to React.createElement")
                            )
                        )
                    )
                )
            ),
            Course(
                id = "course_sql",
                title = "Mastering SQL, Relational Design & Indexing",
                category = "Database",
                instructor = "Sarah Jenkins",
                difficulty = "All Levels",
                duration = "24 Hours",
                rating = 4.9f,
                enrolledCount = 11300,
                progress = 70,
                description = "Learn normalized database schema architecture, multi-table JOINs, subqueries, B-Tree index optimization, and transaction safety.",
                modules = listOf(
                    CourseModule(
                        id = "sql_mod_1",
                        order = 1,
                        title = "01 Advanced Queries & Joins",
                        lessons = listOf(
                            Lesson(
                                id = "sql_1_1",
                                title = "INNER, LEFT, RIGHT, and FULL OUTER JOINs",
                                duration = "25 min",
                                isCompleted = true,
                                content = "Visual mental models and execution plans for relational table joins.",
                                codeExample = "SELECT u.name, COUNT(o.id) AS total_orders\nFROM users u\nLEFT JOIN orders o ON u.id = o.user_id\nGROUP BY u.id\nHAVING total_orders > 3;",
                                keyTakeaways = listOf("Cartesian product prevention", "NULL handling in outer joins", "Aggregation filters with HAVING")
                            )
                        )
                    )
                )
            ),
            Course(
                id = "course_dsa",
                title = "Data Structures & Algorithms Masterclass",
                category = "Computer Science",
                instructor = "Vikram Rao",
                difficulty = "Advanced",
                duration = "50 Hours",
                rating = 4.95f,
                enrolledCount = 18500,
                progress = 35,
                description = "Ace technical coding interviews at top tech companies. Deep dive into Two Pointers, Dynamic Programming, Graphs, and Trees.",
                modules = emptyList()
            )
        )
    )

    val dailySchedule = listOf(
        DailyStudyTask("task_1", "Today", "09:00 AM", "JavaScript – Functions & Closures", "Lesson", 45, isCompleted = true),
        DailyStudyTask("task_2", "Today", "11:00 AM", "Python – OOP Inheritance Practice", "Practice", 60, isCompleted = true),
        DailyStudyTask("task_3", "Today", "03:00 PM", "Database – SQL Joins Assessment", "Quiz", 20, isCompleted = false),
        DailyStudyTask("task_4", "Today", "06:00 PM", "Capstone Project – Weather API Integration", "Project", 90, isCompleted = false)
    )

    val skillsList = listOf(
        SkillItem("Python Programming", 80, 95, "Backend", "Advanced"),
        SkillItem("SQL & Databases", 70, 85, "Database", "Intermediate"),
        SkillItem("JavaScript / TS", 65, 80, "Frontend", "Intermediate"),
        SkillItem("React Framework", 50, 75, "Frontend", "Needs Focus"),
        SkillItem("Data Structures & Alg", 45, 85, "CS Core", "High Gap")
    )

    val codingChallenges = listOf(
        CodingChallenge(
            id = "prob_two_sum",
            title = "Two Sum (Array Target Lookup)",
            difficulty = "Easy",
            tags = listOf("Arrays", "Hash Table"),
            description = "Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.\n\nYou may assume that each input would have exactly one solution, and you may not use the same element twice.\n\nExample:\nInput: nums = [2, 7, 11, 15], target = 9\nOutput: [0, 1] (Because nums[0] + nums[1] == 9)",
            starterCode = mapOf(
                "Python" to "def twoSum(nums, target):\n    # Write your solution here\n    seen = {}\n    for i, n in enumerate(nums):\n        diff = target - n\n        if diff in seen:\n            return [seen[diff], i]\n        seen[n] = i\n    return []",
                "JavaScript" to "function twoSum(nums, target) {\n    const map = new Map();\n    for (let i = 0; i < nums.length; i++) {\n        const diff = target - nums[i];\n        if (map.has(diff)) return [map.get(diff), i];\n        map.set(nums[i], i);\n    }\n    return [];\n}",
                "Java" to "class Solution {\n    public int[] twoSum(int[] nums, int target) {\n        Map<Integer, Integer> map = new HashMap<>();\n        for (int i = 0; i < nums.length; i++) {\n            int diff = target - nums[i];\n            if (map.containsKey(diff)) return new int[]{map.get(diff), i};\n            map.put(nums[i], i);\n        }\n        return new int[]{};\n    }\n}"
            ),
            testCases = listOf(
                TestCase("[2, 7, 11, 15], target = 9", "[0, 1]"),
                TestCase("[3, 2, 4], target = 6", "[1, 2]"),
                TestCase("[3, 3], target = 6", "[0, 1]")
            ),
            solutionExplanation = "By using a Hash Map (dictionary), we can check whether the complement (target - current) exists in O(1) time complexity, reducing total time from O(N^2) to O(N).",
            hints = listOf(
                "Can you store numbers you've already visited in a hash map?",
                "For every number `x`, what you are looking for is `target - x`."
            )
        ),
        CodingChallenge(
            id = "prob_valid_paren",
            title = "Valid Parentheses Matcher",
            difficulty = "Easy",
            tags = listOf("Stack", "Strings"),
            description = "Given a string `s` containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.\n\nOpen brackets must be closed by the same type of brackets, and in the correct order.",
            starterCode = mapOf(
                "Python" to "def isValid(s: str) -> bool:\n    stack = []\n    pairs = {')': '(', '}': '{', ']': '['}\n    for char in s:\n        if char in pairs.values():\n            stack.append(char)\n        elif char in pairs:\n            if not stack or stack.pop() != pairs[char]:\n                return False\n    return len(stack) == 0"
            ),
            testCases = listOf(
                TestCase("\"()[]{}\"", "true"),
                TestCase("\"(]\"", "false"),
                TestCase("\"([{}])\"", "true")
            ),
            solutionExplanation = "A LIFO Stack is optimal here because the most recently opened bracket must be the first one closed.",
            hints = listOf(
                "Push opening brackets to a stack; pop when you see a closing bracket.",
                "Make sure to verify if the stack is empty at the end!"
            )
        )
    )

    val assessmentQuestions = listOf(
        AssessmentQuestion(
            id = "q1",
            question = "In Python, which of the following creates a Generator rather than a populated List?",
            options = listOf(
                "[x * 2 for x in range(10)]",
                "(x * 2 for x in range(10))",
                "{x * 2 for x in range(10)}",
                "list(range(10))"
            ),
            correctIndex = 1,
            explanation = "Parentheses enclosing a comprehension `(x for x in ...)` construct a lazy generator expression, evaluating items on demand without allocating entire memory upfront.",
            topic = "Python Internals",
            difficulty = "Medium"
        ),
        AssessmentQuestion(
            id = "q2",
            question = "Which SQL clause is used to filter records resulting from an aggregate function like COUNT() or SUM()?",
            options = listOf(
                "WHERE",
                "HAVING",
                "ORDER BY",
                "FILTER"
            ),
            correctIndex = 1,
            explanation = "HAVING filters aggregated groups after GROUP BY execution, whereas WHERE filters individual rows prior to grouping.",
            topic = "SQL Joins & Aggregation",
            difficulty = "Easy"
        ),
        AssessmentQuestion(
            id = "q3",
            question = "What is the average time complexity of searching for a key in a balanced Binary Search Tree (AVL / Red-Black)?",
            options = listOf(
                "O(1)",
                "O(log N)",
                "O(N)",
                "O(N log N)"
            ),
            correctIndex = 1,
            explanation = "Because balanced trees maintain height h <= 1.44 * log2(N), the search path cuts search space in half at each step, ensuring O(log N) time.",
            topic = "Data Structures",
            difficulty = "Medium"
        ),
        AssessmentQuestion(
            id = "q4",
            question = "In React, what will trigger a component re-render?",
            options = listOf(
                "Modifying a local variable with let count = 10",
                "Calling setState() or updating a useState hook value",
                "Adding comments in JSX",
                "Importing a CSS module"
            ),
            correctIndex = 1,
            explanation = "React schedules re-renders when state or props mutate via updater functions like `setCount` in useState or useReducer.",
            topic = "React Architecture",
            difficulty = "Easy"
        ),
        AssessmentQuestion(
            id = "q5",
            question = "Which OOP concept is demonstrated when a subclass provides a specific implementation of a method defined in its parent class?",
            options = listOf(
                "Method Overloading",
                "Method Overriding (Polymorphism)",
                "Encapsulation",
                "Multiple Inheritance"
            ),
            correctIndex = 1,
            explanation = "Method Overriding allows a derived class to customize behavior inherited from a superclass, enabling runtime dynamic dispatch.",
            topic = "Object-Oriented Design",
            difficulty = "Easy"
        )
    )

    val projects = listOf(
        ProjectItem(
            id = "proj_weather",
            title = "AI Smart Weather & Outfit Assistant",
            level = "Beginner",
            techStack = listOf("Python", "REST API", "JSON", "Tkinter"),
            description = "Build a desktop application fetching live meteorological telemetry and calling AI to recommend daily attire based on humidity, temperature, and UV index.",
            milestones = listOf("Setup OpenWeatherMap API", "Parse JSON Payload", "Build UI Dashboard", "Integrate AI Outfit Suggester"),
            status = "In Progress",
            feedback = "Great modular design on API fetcher! Consider adding caching for repeated city queries.",
            rubricScore = 85
        ),
        ProjectItem(
            id = "proj_chatbot",
            title = "Context-Aware RAG Knowledge Bot",
            level = "Advanced",
            techStack = listOf("Python", "FastAPI", "Vector Embeddings", "LangChain"),
            description = "Develop an intelligent customer documentation query engine indexing technical PDF manuals into vector space for sub-second retrieval.",
            milestones = listOf("Document chunking & tokenization", "Cosine similarity search", "FastAPI endpoint setup", "Streaming chat interface"),
            status = "Not Started"
        ),
        ProjectItem(
            id = "proj_portfolio",
            title = "High-Performance Developer Portfolio",
            level = "Beginner",
            techStack = listOf("React", "Tailwind CSS", "TypeScript", "Vite"),
            description = "Create a responsive, accessible portfolio showcasing projects, verified credentials, interactive code sandbox, and live GitHub stats.",
            milestones = listOf("Mobile-first UI design", "Theme switcher (Dark/Light)", "GitHub REST API stats", "Interactive contact form"),
            status = "Evaluated",
            feedback = "Exceptional UI polish, perfect Lighthouse performance scores, and crisp animations!",
            rubricScore = 96
        )
    )

    val studentList = listOf(
        StudentAttendance("st_1", "Alex Morgan", "CSE-2024-01", AttendanceStatus.PRESENT, 14, 98),
        StudentAttendance("st_2", "Rohan Mehta", "CSE-2024-02", AttendanceStatus.PRESENT, 9, 92),
        StudentAttendance("st_3", "Ananya Reddy", "CSE-2024-03", AttendanceStatus.LATE, 4, 85),
        StudentAttendance("st_4", "David Kim", "CSE-2024-04", AttendanceStatus.ABSENT, 0, 72),
        StudentAttendance("st_5", "Sneha Patel", "CSE-2024-05", AttendanceStatus.PRESENT, 12, 95)
    )

    val badges = listOf(
        BadgeItem("b1", "7-Day Streak Master", "Maintained consistent daily learning for a full week", "🔥", true, "Yesterday"),
        BadgeItem("b2", "Python Guru", "Completed 5 core Python modules with score > 90%", "🏆", true, "3 days ago"),
        BadgeItem("b3", "Algorithm Ace", "Solved 25 coding challenges without revealing solutions", "⚡", true, "Last week"),
        BadgeItem("b4", "Capstone Architect", "Shipped a full-stack evaluated project", "🚀", false)
    )

    val certificate = CertificateItem(
        id = "cert_py_2026",
        studentName = "Alex Morgan",
        courseName = "Python Programming & System Design",
        completionDate = "September 18, 2026",
        instructor = "Dr. Priya Sharma (SmartEducation Lead)",
        credentialId = "SMARTEDU-VERIFIED-89241X",
        gradeScore = "Grade A+ (Distinction - 94%)"
    )

    val notifications = listOf(
        NotificationItem("n1", "Quiz Available: SQL Joins Mastery", "Your scheduled 15-minute diagnostic quiz is ready to attempt.", "10m ago", "quiz"),
        NotificationItem("n2", "🔥 Streak Maintained!", "You're on a 7-day streak! Don't forget tomorrow's session to reach Level 5.", "2h ago", "streak"),
        NotificationItem("n3", "AI Recommendation", "Based on your recent assessment, review 'Recursion in Functions' before next project.", "5h ago", "recommendation"),
        NotificationItem("n4", "Project Evaluation Released", "Dr. Priya Sharma posted rubric feedback for 'Developer Portfolio'.", "1d ago", "assignment")
    )
}
