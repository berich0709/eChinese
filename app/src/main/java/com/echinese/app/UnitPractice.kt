package com.echinese.app

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.echinese.core.LocalTokens

@Composable
fun UnitScreen(onStart: () -> Unit, onBack: () -> Unit) {
    val t = LocalTokens.current
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("‹", style = MaterialTheme.typography.headlineSmall) }
            Spacer(Modifier.weight(1f))
            Text("HSK 2", color = t.muted)
        }
        Text("UNIT 1 · MAKANAN & MINUMAN", color = t.brand, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text("Di pasar", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Belanja kebutuhan harian dan tanyakan harga.", color = t.muted)
        Spacer(Modifier.height(10.dp))
        LinearProgressIndicator(progress = { 0.4f }, modifier = Modifier.fillMaxWidth().height(8.dp))
        Spacer(Modifier.height(18.dp))

        listOf(
            Triple("Kosakata", "10 kata · 8 selesai", "📘"),
            Triple("Tata bahasa", "3 pola · 2 selesai", "🧩"),
            Triple("Mendengarkan", "8 soal · 6 selesai", "🎧"),
            Triple("Membaca", "2 teks · 0 selesai", "📖")
        ).forEach { (title, sub, icon) ->
            Card(Modifier.fillMaxWidth().padding(bottom = 10.dp), shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(44.dp).background(t.brandSoft, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                        Text(icon)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(title, fontWeight = FontWeight.SemiBold)
                        Text(sub, color = t.muted, style = MaterialTheme.typography.labelSmall)
                    }
                    Text("›", color = t.muted)
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(t.buttonHeight.dp)) {
            Text("Mulai latihan")
        }
    }
}

@Composable
fun PracticeScreen(onDone: () -> Unit, onBack: () -> Unit) {
    val t = LocalTokens.current
    var selected by remember { mutableStateOf<Int?>(null) }
    var checked by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("✕", style = MaterialTheme.typography.titleMedium) }
            Spacer(Modifier.weight(1f))
            Text("3 / 10", color = t.muted)
        }
        LinearProgressIndicator(progress = { 0.3f }, modifier = Modifier.fillMaxWidth().height(6.dp))
        Spacer(Modifier.height(20.dp))
        Text("Pilih yang kamu dengar", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = {}, shape = RoundedCornerShape(12.dp)) { Text("🔊") }
            OutlinedButton(onClick = {}, shape = RoundedCornerShape(12.dp)) { Text("🐢") }
        }
        Spacer(Modifier.height(20.dp))
        listOf("茶  chá", "和  hé", "咖啡  kā fēi").forEachIndexed { i, label ->
            val isSel = selected == i
            Card(
                Modifier.fillMaxWidth().padding(bottom = 10.dp).clickable { if (!checked) selected = i },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSel) t.brandSoft else Color.White
                ),
                border = if (isSel) androidx.compose.foundation.BorderStroke(2.dp, t.brand) else null
            ) {
                Text(label, Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            }
        }
        if (checked) {
            val ok = selected == 0
            Text(if (ok) "Benar!" else "Belum tepat", color = if (ok) t.success else t.danger, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = { if (!checked) checked = true else if (checked) onDone() },
            enabled = selected != null,
            modifier = Modifier.fillMaxWidth().height(t.buttonHeight.dp)
        ) { Text(if (checked) "Lanjut" else "Periksa") }
    }
}
