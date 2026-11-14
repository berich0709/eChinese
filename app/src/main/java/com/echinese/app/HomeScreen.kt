package com.echinese.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.echinese.core.Brand
import com.echinese.core.BrandSoft
import com.echinese.core.Lang
import com.echinese.core.LocalStrings
import com.echinese.core.LocalTokens
import com.echinese.core.Muted
import com.echinese.domain.DailyTask
import com.echinese.domain.User



@Composable
fun HomeScreen(user: User, onLogout: () -> Unit, onLang: (Lang) -> Unit) {
    val s = LocalStrings.current
    val tabs = listOf(s.tabToday to "☀", s.tabLearn to "📘", s.tabReview to "🔁", s.tabMe to "◐")
    var tab by remember { mutableStateOf(0) }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                tabs.forEachIndexed { i, (label, icon) ->
                    NavigationBarItem(
                        selected = tab == i,
                        onClick = { tab = i },
                        icon = { Text(icon) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { pad ->
        Column(Modifier.padding(pad).padding(horizontal = 20.dp).verticalScroll(rememberScrollState()).statusBarsPadding()) {
            Spacer(Modifier.height(16.dp))
            Text(s.hello + ",", color = Muted)
            Text(user.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            when (tab) {
                0 -> TodayTab()
                1 -> CoursePathScreen()
                2 -> Placeholder("🔁", "Ulangi", "Daftar ulangan akan tampil di sini.")
                else -> ProfileTab(user, onLogout, onLang, s)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TodayTab() {
    val s = LocalStrings.current
    val t = LocalTokens.current
    val tasks = listOf(
        DailyTask("1", "Kosakata makanan", false, 0.2f),
        DailyTask("2", "Mendengarkan harga", false, 0f),
        DailyTask("3", "Kuis mini", true, 1f)
    )
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = t.brand)) {
        Column(Modifier.padding(18.dp)) {
            Text("TARGET HARI INI", color = Color.White.copy(alpha = .7f), style = MaterialTheme.typography.labelSmall)
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text("18", color = Color.White, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                Text(" / 20 min", color = Color.White.copy(alpha = .8f), modifier = Modifier.padding(bottom = 6.dp))
                Spacer(Modifier.weight(1f))
                Text("🔥 12", color = t.gold, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(10.dp))
            LinearProgressIndicator(progress = { 0.9f }, modifier = Modifier.fillMaxWidth().height(8.dp),
                color = t.gold, trackColor = Color.White.copy(alpha = .25f))
            Spacer(Modifier.height(10.dp))
            Text("12 hari beruntun · Menuju HSK 2", color = Color.White.copy(alpha = .8f),
                style = MaterialTheme.typography.labelSmall)
        }
    }
    Spacer(Modifier.height(18.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(s.todayPlan, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.weight(1f))
        Text("2 dari 3 selesai", color = t.muted, style = MaterialTheme.typography.labelSmall)
    }
    Spacer(Modifier.height(8.dp))
    tasks.forEach { task -> TaskCard(task); Spacer(Modifier.height(10.dp)) }
    Spacer(Modifier.height(4.dp))
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("📅", modifier = Modifier.padding(end = 10.dp))
            Column(Modifier.weight(1f)) {
                Text("Ujian HSK 2 · 119 hari lagi", fontWeight = FontWeight.SemiBold)
                Text("Persiapan ujian dimulai", color = t.muted, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun TaskCard(t: DailyTask) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(44.dp), contentAlignment = Alignment.Center) {
                Surface(color = BrandSoft, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxSize()) {
                    Box(contentAlignment = Alignment.Center) { Text(if (t.done) "✓" else "•", color = Brand) }
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(t.title, fontWeight = FontWeight.SemiBold)
                LinearProgressIndicator(progress = { t.progress }, modifier = Modifier.fillMaxWidth().padding(top = 6.dp))
            }
        }
    }
}

@Composable
private fun ProfileTab(user: User, onLogout: () -> Unit, onLang: (Lang) -> Unit, s: com.echinese.core.Strings) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Text(user.name, fontWeight = FontWeight.Bold)
            Text(user.email, color = Muted)
        }
    }
    Spacer(Modifier.height(12.dp))
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.padding(16.dp)) {
            Text(s.langLabel, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = { onLang(Lang.ID) }, label = { Text("Bahasa Indonesia") })
                AssistChip(onClick = { onLang(Lang.EN) }, label = { Text("English") })
                AssistChip(onClick = { onLang(Lang.ZH) }, label = { Text("中文") })
            }
        }
    }
    Spacer(Modifier.height(16.dp))
    OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth()) { Text(s.logout) }
}

@Composable
private fun Placeholder(icon: String, title: String, desc: String) {
    Column(Modifier.fillMaxWidth().padding(top = 40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(8.dp))
        Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(desc, color = Muted)
    }
}
