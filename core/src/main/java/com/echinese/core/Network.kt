package com.echinese.core

/** 所有网络请求的唯一入口；真实接口就绪前由 MockApi 实现。 */
interface Api {
    suspend fun login(email: String, password: String): Result<Session>
    suspend fun register(name: String, email: String, password: String): Result<Session>
}

data class Session(val token: String, val userId: String, val name: String)
