package com.echinese.domain

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(name: String, email: String, password: String): Result<User>
}
interface PlanRepository { suspend fun today(): List<DailyTask> }
