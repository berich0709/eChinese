package com.echinese.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.echinese.core.Danger
import com.echinese.core.LocalStrings
import com.echinese.core.LocalTokens
import com.echinese.data.MockAuthRepository
import com.echinese.domain.User
import kotlinx.coroutines.launch

private enum class Mode { LOGIN, REGISTER, FORGOT }

@Composable
fun AuthFlow(onLoggedIn: (User, Boolean) -> Unit) {
    val s = LocalStrings.current
    val t = LocalTokens.current
    var mode by remember { mutableStateOf(Mode.LOGIN) }
    val repo = remember { MockAuthRepository() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("demo@hsk.id") }
    var password by remember { mutableStateOf("123456") }
    var error by remember { mutableStateOf<String?>(null) }
    var info by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize().background(t.bg).verticalScroll(rememberScrollState()).padding(24.dp).statusBarsPadding(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.size(64.dp).clip(RoundedCornerShape(20.dp)).background(t.brand),
                contentAlignment = Alignment.Center
            ) { Text("eC", color = Color.White, fontWeight = FontWeight.Bold) }
            Spacer(Modifier.height(12.dp))
            Text(s.appName, color = t.brand, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.height(24.dp))

        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    when (mode) {
                        Mode.LOGIN -> s.loginTitle; Mode.REGISTER -> s.registerTitle; Mode.FORGOT -> s.forgotTitle
                    },
                    style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    when (mode) {
                        Mode.LOGIN -> s.loginSub; Mode.REGISTER -> s.registerSub; Mode.FORGOT -> s.forgotSub
                    }, color = t.muted, style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(18.dp))

                if (mode == Mode.REGISTER) {
                    OutlinedTextField(name, { name = it; error = null; info = null }, label = { Text(s.name) },
                        singleLine = true, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(12.dp))
                }
                OutlinedTextField(email, { email = it; error = null; info = null }, label = { Text(s.email) },
                    singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth())
                if (mode != Mode.FORGOT) {
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(password, { password = it; error = null; info = null },
                        label = { Text(s.password) }, singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth())
                }

                error?.let { Spacer(Modifier.height(8.dp)); Text(it, color = Danger) }
                info?.let { Spacer(Modifier.height(8.dp)); Text(it, color = t.brand) }
                Spacer(Modifier.height(18.dp))

                val emailOk = Regex("^\\S+@\\S+\\.\\S+$").matches(email.trim())
                val valid = when (mode) {
                    Mode.LOGIN, Mode.REGISTER -> emailOk && password.length >= 6 && (mode == Mode.LOGIN || name.isNotBlank())
                    Mode.FORGOT -> emailOk
                }

                Button(
                    onClick = {
                        error = null; info = null; loading = true
                        scope.launch {
                            when (mode) {
                                Mode.LOGIN -> repo.login(email.trim(), password)
                                    .onSuccess { onLoggedIn(it, false) }
                                    .onFailure { e ->
                                        error = when (e.message) {
                                            "NO_USER" -> s.errNoUser; "BAD_PASSWORD" -> s.errBadPass; else -> s.errFail
                                        }
                                    }
                                Mode.REGISTER -> repo.register(name.trim(), email.trim(), password)
                                    .onSuccess { onLoggedIn(it, true) }
                                    .onFailure { e -> error = if (e.message == "EMAIL_TAKEN") s.errTaken else s.errFail }
                                Mode.FORGOT -> info = s.forgotSent
                            }
                            loading = false
                        }
                    },
                    enabled = !loading && valid,
                    modifier = Modifier.fillMaxWidth().height(t.buttonHeight.dp)
                ) {
                    if (loading) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp, color = Color.White)
                    else Text(when (mode) { Mode.LOGIN -> s.login; Mode.REGISTER -> s.register; Mode.FORGOT -> s.sendLink })
                }

                Spacer(Modifier.height(6.dp))
                when (mode) {
                    Mode.LOGIN -> {
                        TextButton({ error = null; info = null; mode = Mode.FORGOT }) { Text(s.forgot, color = t.brand) }
                        TextButton({
                            error = null; info = null; name = ""; email = ""; password = ""; mode = Mode.REGISTER
                        }) { Text(s.noAccount, color = t.brand) }
                    }
                    Mode.REGISTER -> TextButton({
                        error = null; info = null; email = "demo@hsk.id"; password = "123456"; mode = Mode.LOGIN
                    }) { Text(s.hasAccount, color = t.brand) }
                    Mode.FORGOT -> TextButton({ error = null; info = null; mode = Mode.LOGIN }) {
                        Text(s.backLogin, color = t.brand)
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        if (mode == Mode.LOGIN) {
            Text(s.demoHint, color = t.muted, style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
