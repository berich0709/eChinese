package com.echinese.domain

data class User(val id: String, val name: String, val email: String, val level: String = "HSK2")
data class Goal(val level: String, val examDate: String, val minutes: Int)
data class DailyTask(val id: String, val title: String, val done: Boolean, val progress: Float)
