package com.echinese.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.echinese.core.Brand
import com.echinese.core.BrandSoft
import com.echinese.core.Muted
import com.echinese.domain.DailyTask
import com.echinese.domain.User

private val tabs = listOf("今天" to "☀", "学习" to "📘", "复习" to "🔁", "我的" to "◐")

@Composable
fun HomeScreen(user: User, onLogout: () -> Unit) {
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
        Column(Modifier.padding(pad).padding(horizontal = 20.dp).verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(16.dp))
            Text("Halo,", color = Muted)
            Text(user.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
            when (tab) {
                0 -> TodayTab()
                1 -> Placeholder("📘", "Belajar", "Daftar unit akan tampil di sini.")
                2 -> Placeholder("🔁", "Ulangi", "Daftar ulangan akan tampil di sini.")
                else -> ProfileTab(user, onLogout)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TodayTab() {
    val tasks = listOf(
        DailyTask("1", "Kosakata HSK2 · 10 kata", false, 0.2f),
        DailyTask("2", "Ulangi 12 kata", false, 0f),
        DailyTask("3", "Kuis mini · 10 soal", true, 1f)
    )
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text("🔥 4 hari beruntun", color = Brand, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text("Target: HSK2 · 20 menit", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("Ujian: 14 Jun 2027", color = Muted)
        }
    }
    Spacer(Modifier.height(12.dp))
    Text("Rencana hari ini", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    Spacer(Modifier.height(8.dp))
    tasks.forEach { t -> TaskCard(t); Spacer(Modifier.height(10.dp)) }
}

@Composable
private fun TaskCard(t: DailyTask) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp)) {
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
private fun ProfileTab(user: User, onLogout: () -> Unit) {
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(user.name, fontWeight = FontWeight.Bold)
            Text(user.email, color = Muted)
        }
    }
    Spacer(Modifier.height(12.dp))
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text("Bahasa antarmuka", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text("Bahasa Indonesia") })
                AssistChip(onClick = {}, label = { Text("中文") })
            }
        }
    }
    Spacer(Modifier.height(16.dp))
    OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth()) { Text("Keluar") }
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
