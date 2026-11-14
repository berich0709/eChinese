package com.echinese.app

import android.content.Context
import com.echinese.domain.User

/** 登录态本地存储；接入真实接口后 token 也放这里。 */
class SessionStore(context: Context) {
    private val sp = context.getSharedPreferences("eChinese.session", Context.MODE_PRIVATE)

    fun save(user: User) {
        sp.edit().putBoolean("logged_in", true)
            .putString("id", user.id).putString("name", user.name)
            .putString("email", user.email).putString("level", user.level).apply()
    }

    fun user(): User? = if (sp.getBoolean("logged_in", false)) User(
        id = sp.getString("id", "") ?: "",
        name = sp.getString("name", "") ?: "",
        email = sp.getString("email", "") ?: "",
        level = sp.getString("level", "HSK2") ?: "HSK2"
    ) else null

    fun saveGoal(level: String, examDate: String, minutes: Int) {
        sp.edit().putString("level", level).putString("exam_date", examDate)
            .putInt("minutes", minutes).putBoolean("goal_set", true).apply()
    }

    fun goalSet() = sp.getBoolean("goal_set", false)

    fun clear() = sp.edit().clear().apply()
}
