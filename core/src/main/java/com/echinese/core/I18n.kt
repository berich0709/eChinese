package com.echinese.core

import androidx.compose.runtime.staticCompositionLocalOf

enum class Lang(val code: String) { ZH("zh"), EN("en"), ID("id") }

/** 所有界面文案；新增语言只需加一个 Strings 实例，页面不改。 */
data class Strings(
    val appName: String, val loginTitle: String, val loginSub: String,
    val registerTitle: String, val registerSub: String,
    val forgotTitle: String, val forgotSub: String,
    val name: String, val email: String, val password: String,
    val login: String, val register: String, val sendLink: String,
    val forgot: String, val noAccount: String, val hasAccount: String, val backLogin: String,
    val errNoUser: String, val errBadPass: String, val errTaken: String, val errFail: String,
    val forgotSent: String, val demoHint: String,
    val tabToday: String, val tabLearn: String, val tabReview: String, val tabMe: String,
    val hello: String, val todayPlan: String, val streak: String, val target: String, val exam: String,
    val logout: String, val langLabel: String, val profile: String,
    val goalTitle: String, val goalSub: String, val level: String, val examDate: String,
    val minutes: String, val saveStart: String,
    val pathTitle: String, val pathSub: String, val startLearning: String, val now: String,
    val targetToday: String, val streakLine: String, val tasksDone: String,
    val examLine: String, val examSub: String
)

val ZhStrings = Strings(
    appName = "eChinese",
    loginTitle = "欢迎回来", loginSub = "登录后继续你的 HSK 学习",
    registerTitle = "创建账号", registerSub = "保存进度与学习计划",
    forgotTitle = "重置密码", forgotSub = "我们会把重置链接发到你的邮箱",
    name = "昵称", email = "邮箱", password = "密码",
    login = "登录", register = "注册", sendLink = "发送链接",
    forgot = "忘记密码？", noAccount = "没有账号？注册", hasAccount = "已有账号？登录", backLogin = "返回登录",
    errNoUser = "账号不存在", errBadPass = "密码错误", errTaken = "邮箱已注册", errFail = "操作失败，请重试",
    forgotSent = "重置链接已发送（演示）", demoHint = "演示账号：demo@hsk.id / 123456",
    tabToday = "今天", tabLearn = "学习", tabReview = "复习", tabMe = "我的",
    hello = "你好", todayPlan = "今日计划", streak = "天连续学习", target = "目标", exam = "考试",
    logout = "退出登录", langLabel = "界面语言", profile = "我的",
    goalTitle = "设置学习目标", goalSub = "我们会根据目标和每日时间生成计划",
    level = "HSK 等级", examDate = "考试日期", minutes = "每日学习时长", saveStart = "保存并开始",
    pathTitle = "学习路径", pathSub = "HSK 2 · 基础", startLearning = "继续学习", now = "当前",
    targetToday = "今日目标", streakLine = "连续 12 天 · 冲刺 HSK 2", tasksDone = "已完成 2/3",
    examLine = "HSK 2 考试 · 还剩 119 天", examSub = "开始备考"
)
val EnStrings = ZhStrings.copy(
    loginTitle = "Welcome back", loginSub = "Sign in to continue your HSK study",
    registerTitle = "Create account", registerSub = "Save your progress and plan",
    forgotTitle = "Reset password", forgotSub = "We'll email you a reset link",
    name = "Nickname", email = "Email", password = "Password",
    login = "Sign in", register = "Sign up", sendLink = "Send link",
    forgot = "Forgot password?", noAccount = "No account? Sign up", hasAccount = "Have an account? Sign in", backLogin = "Back to sign in",
    errNoUser = "Account not found", errBadPass = "Wrong password", errTaken = "Email already registered", errFail = "Failed, please retry",
    forgotSent = "Reset link sent (demo)", demoHint = "Demo: demo@hsk.id / 123456",
    tabToday = "Today", tabLearn = "Learn", tabReview = "Review", tabMe = "Me",
    hello = "Hello", todayPlan = "Today's plan", streak = "day streak", target = "Target", exam = "Exam",
    logout = "Sign out", langLabel = "Language", profile = "Me",
    goalTitle = "Set your goal", goalSub = "We'll build a daily plan around your goal",
    level = "HSK level", examDate = "Exam date", minutes = "Daily study time", saveStart = "Save & start",
    pathTitle = "Learning path", pathSub = "HSK 2 · Basic", startLearning = "Continue learning", now = "Now",
    targetToday = "TODAY'S TARGET", streakLine = "12 day streak · Toward HSK 2", tasksDone = "2 of 3 done",
    examLine = "HSK 2 exam · 119 days left", examSub = "Exam prep started"
)
val IdStrings = ZhStrings.copy(
    loginTitle = "Selamat datang", loginSub = "Masuk untuk melanjutkan belajar HSK",
    registerTitle = "Buat akun", registerSub = "Simpan progres dan rencana belajarmu",
    forgotTitle = "Atur ulang kata sandi", forgotSub = "Kami kirim tautan ke emailmu",
    name = "Nama panggilan", email = "Email", password = "Kata sandi",
    login = "Masuk", register = "Daftar", sendLink = "Kirim tautan",
    forgot = "Lupa kata sandi?", noAccount = "Belum punya akun? Daftar", hasAccount = "Sudah punya akun? Masuk", backLogin = "Kembali masuk",
    errNoUser = "Akun tidak ditemukan", errBadPass = "Kata sandi salah", errTaken = "Email sudah terdaftar", errFail = "Gagal, coba lagi",
    forgotSent = "Tautan sudah dikirim (contoh)", demoHint = "Demo: demo@hsk.id / 123456",
    tabToday = "Hari ini", tabLearn = "Belajar", tabReview = "Ulangi", tabMe = "Saya",
    hello = "Halo", todayPlan = "Rencana hari ini", streak = "hari beruntun", target = "Target", exam = "Ujian",
    logout = "Keluar", langLabel = "Bahasa", profile = "Saya",
    goalTitle = "Atur target belajar", goalSub = "Kami susun rencana harian sesuai waktumu",
    level = "Level HSK", examDate = "Tanggal ujian", minutes = "Belajar per hari", saveStart = "Simpan & mulai",
    pathTitle = "Jalur belajar", pathSub = "HSK 2 · Dasar", startLearning = "Lanjutkan belajar", now = "SEKARANG",
    targetToday = "TARGET HARI INI", streakLine = "12 hari beruntun · Menuju HSK 2", tasksDone = "2 dari 3 selesai",
    examLine = "Ujian HSK 2 · 119 hari lagi", examSub = "Persiapan ujian dimulai"
)

fun stringsFor(lang: Lang) = when (lang) {
    Lang.ZH -> ZhStrings; Lang.EN -> EnStrings; Lang.ID -> IdStrings
}

val LocalStrings = staticCompositionLocalOf { ZhStrings }
