package com.echinese.data

import com.echinese.domain.AuthRepository
import com.echinese.domain.DailyTask
import com.echinese.domain.PlanRepository
import com.echinese.domain.User
import kotlinx.coroutines.delay

class MockAuthRepository : AuthRepository {
    private val users = mutableMapOf("demo@hsk.id" to ("123456" to User("u_demo", "Sinta", "demo@hsk.id")))
    override suspend fun login(email: String, password: String): Result<User> {
        delay(300)
        val hit = users[email.lowercase()] ?: return Result.failure(IllegalStateException("NO_USER"))
        if (hit.first != password) return Result.failure(IllegalStateException("BAD_PASSWORD"))
        return Result.success(hit.second)
    }
    override suspend fun register(name: String, email: String, password: String): Result<User> {
        delay(300)
        if (users.containsKey(email.lowercase())) return Result.failure(IllegalStateException("EMAIL_TAKEN"))
        val u = User("u_" + email.hashCode(), name, email); users[email.lowercase()] = password to u
        return Result.success(u)
    }
}
class MockPlanRepository : PlanRepository {
    override suspend fun today(): List<DailyTask> { delay(200); return listOf(
        DailyTask("t1", "Kosakata HSK2 · 10 kata", false, 0.2f),
        DailyTask("t2", "Ulangi 12 kata", false, 0f),
        DailyTask("t3", "Kuis mini · 10 soal", true, 1f)) }
}
