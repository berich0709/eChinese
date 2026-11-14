package com.echinese.app

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.echinese.core.LocalStrings
import com.echinese.core.LocalTokens
import com.echinese.domain.User

@Composable
fun GoalScreen(user: User, onDone: (level: String, examDate: String, minutes: Int) -> Unit) {
    val tokens = LocalTokens.current
    val s = LocalStrings.current
    var level by remember { mutableStateOf(user.level) }
    var date by remember { mutableStateOf("2027-06-14") }
    var minutes by remember { mutableStateOf(20) }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp).statusBarsPadding()
    ) {
        Spacer(Modifier.height(24.dp))
        Text(s.goalTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(s.goalSub, color = tokens.muted)
        Spacer(Modifier.height(24.dp))

        Text(s.level, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("HSK1", "HSK2", "HSK3").forEach { l ->
                FilterChip(selected = level == l, onClick = { level = l }, label = { Text(l) })
            }
        }

        Spacer(Modifier.height(20.dp))
        Text(s.examDate, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(date, { date = it }, singleLine = true,
            label = { Text("YYYY-MM-DD") }, modifier = Modifier.fillMaxWidth())

        Spacer(Modifier.height(20.dp))
        Text(s.minutes, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(10, 20, 30).forEach { m ->
                FilterChip(selected = minutes == m, onClick = { minutes = m }, label = { Text("$m min") })
            }
        }

        Spacer(Modifier.height(36.dp))
        Button(
            onClick = { onDone(level, date, minutes) },
            modifier = Modifier.fillMaxWidth().height(tokens.buttonHeight.dp)
        ) { Text(s.saveStart) }
    }
}
