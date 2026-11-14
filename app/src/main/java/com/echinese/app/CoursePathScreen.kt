package com.echinese.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.echinese.core.LocalStrings
import com.echinese.core.LocalTokens

private enum class NodeState { DONE, CURRENT, LOCKED }
private data class Lesson(val title: String, val state: NodeState, val sub: String = "")

private val unit1 = listOf(
    Lesson("Kosakata makanan", NodeState.DONE, "8 dari 8 kata"),
    Lesson("Mendengar harga", NodeState.DONE, "6 dari 6 soal"),
    Lesson("Di pasar", NodeState.CURRENT, "2 dari 5 latihan"),
    Lesson("Membandingkan harga", NodeState.LOCKED),
    Lesson("Ulasan unit", NodeState.LOCKED)
)

@Composable
fun CoursePathScreen() {
    val t = LocalTokens.current
    val s = LocalStrings.current
    Column(Modifier.fillMaxWidth()) {
        Text(s.pathTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(4.dp))
        Text(s.pathSub, color = t.muted)
        Spacer(Modifier.height(12.dp))
        LinearProgressIndicator(progress = { 0.42f }, modifier = Modifier.fillMaxWidth().height(8.dp))
        Spacer(Modifier.height(20.dp))

        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(16.dp)) {
                Text("UNIT 1 · MAKANAN & MINUMAN", color = t.brand, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.height(6.dp))
                Text("Di pasar", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Belanja kebutuhan harian dan tanyakan harga.", color = t.muted)
                Spacer(Modifier.height(10.dp))
                Text("2 dari 5 pelajaran", color = t.muted, style = MaterialTheme.typography.labelSmall)
                Spacer(Modifier.height(16.dp))

                unit1.forEachIndexed { i, lesson -> LessonRow(lesson, isLast = i == unit1.lastIndex) }
            }
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(t.buttonHeight.dp)) {
            Text(s.startLearning)
        }
    }
}

@Composable
private fun LessonRow(lesson: Lesson, isLast: Boolean) {
    val t = LocalTokens.current
    val s = LocalStrings.current
    Row(Modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(44.dp)) {
            val bg = when (lesson.state) {
                NodeState.DONE -> t.success
                NodeState.CURRENT -> t.brand
                NodeState.LOCKED -> t.line
            }
            val fg = if (lesson.state == NodeState.LOCKED) t.muted else Color.White
            Box(Modifier.size(38.dp).background(bg, CircleShape).border(3.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center) {
                Text(when (lesson.state) {
                    NodeState.DONE -> "✓"; NodeState.CURRENT -> "▶"; NodeState.LOCKED -> "🔒"
                }, color = fg, style = MaterialTheme.typography.labelMedium)
            }
            if (!isLast) Box(Modifier.width(2.dp).height(34.dp).background(t.line))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.padding(bottom = 14.dp)) {
            Text(
                lesson.title,
                fontWeight = if (lesson.state == NodeState.CURRENT) FontWeight.Bold else FontWeight.Medium,
                color = if (lesson.state == NodeState.LOCKED) t.muted else t.ink
            )
            if (lesson.state == NodeState.CURRENT) {
                Text(s.now, color = t.brand, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            } else if (lesson.sub.isNotEmpty()) {
                Text(lesson.sub, color = t.muted, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
