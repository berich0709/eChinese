package com.echinese.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.echinese.core.Brand
import com.echinese.core.Danger
import com.echinese.core.Muted
import com.echinese.data.MockAuthRepository
import kotlinx.coroutines.launch

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val repo = remember { MockAuthRepository() }

    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text("Halo, selamat datang", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Masuk untuk melanjutkan belajar HSK", color = Muted)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(password, { password = it }, label = { Text("Kata sandi") }, singleLine = true,
            visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        if (error != null) { Spacer(Modifier.height(8.dp)); Text(error!!, color = Danger) }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            error = null; loading = true
            scope.launch {
                val r = repo.login(email.trim(), password)
                loading = false
                r.onFailure { e -> error = when (e.message) {
                    "NO_USER" -> "Akun tidak ditemukan"; "BAD_PASSWORD" -> "Kata sandi salah"; else -> "Gagal masuk" } }
            }
        }, enabled = !loading && email.isNotBlank() && password.length >= 6,
            modifier = Modifier.fillMaxWidth().height(50.dp)) {
            if (loading) CircularProgressIndicator(Modifier.size(18.dp), color = androidx.compose.ui.graphics.Color.White, strokeWidth = 2.dp)
            else Text("Masuk")
        }
        Spacer(Modifier.height(10.dp))
        TextButton(onClick = {}) { Text("Belum punya akun? Daftar", color = Brand) }
        Spacer(Modifier.weight(1f))
        Text("Demo: demo@hsk.id / 123456", color = Muted, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}
