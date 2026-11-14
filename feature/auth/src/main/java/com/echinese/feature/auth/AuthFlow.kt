package com.echinese.feature.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.echinese.core.Brand
import com.echinese.core.Danger
import com.echinese.core.Muted
import com.echinese.data.MockAuthRepository
import com.echinese.domain.User
import kotlinx.coroutines.launch

private enum class Mode { LOGIN, REGISTER, FORGOT }

@Composable
fun AuthFlow(onLoggedIn: (User, Boolean) -> Unit) {
    var mode by remember { mutableStateOf(Mode.LOGIN) }
    val repo = remember { MockAuthRepository() }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var info by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    fun reset() { error = null; info = null }

    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            when (mode) {
                Mode.LOGIN -> "Halo, selamat datang"
                Mode.REGISTER -> "Buat akun eChinese"
                Mode.FORGOT -> "Atur ulang kata sandi"
            },
            style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(6.dp))
        Text(
            when (mode) {
                Mode.LOGIN -> "Masuk untuk melanjutkan belajar HSK"
                Mode.REGISTER -> "Simpan progres dan rencana belajarmu"
                Mode.FORGOT -> "Kami kirim tautan atur ulang ke emailmu"
            }, color = Muted
        )
        Spacer(Modifier.height(24.dp))

        if (mode == Mode.REGISTER) {
            OutlinedTextField(name, { name = it; reset() }, label = { Text("Nama panggilan") },
                singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
        }
        OutlinedTextField(email, { email = it; reset() }, label = { Text("Email") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        if (mode != Mode.FORGOT) {
            OutlinedTextField(password, { password = it; reset() }, label = { Text("Kata sandi") },
                singleLine = true, visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth())
        }

        error?.let { Spacer(Modifier.height(8.dp)); Text(it, color = Danger) }
        info?.let { Spacer(Modifier.height(8.dp)); Text(it, color = Brand) }
        Spacer(Modifier.height(20.dp))

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
                                    "NO_USER" -> "Akun tidak ditemukan"
                                    "BAD_PASSWORD" -> "Kata sandi salah"
                                    else -> "Gagal masuk"
                                }
                            }
                        Mode.REGISTER -> repo.register(name.trim(), email.trim(), password)
                            .onSuccess { onLoggedIn(it, true) }
                            .onFailure { e ->
                                error = if (e.message == "EMAIL_TAKEN") "Email sudah terdaftar" else "Gagal mendaftar"
                            }
                        Mode.FORGOT -> info = "Tautan atur ulang sudah dikirim (contoh)."
                    }
                    loading = false
                }
            },
            enabled = !loading && valid,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            if (loading) CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp,
                color = androidx.compose.ui.graphics.Color.White)
            else Text(
                when (mode) {
                    Mode.LOGIN -> "Masuk"; Mode.REGISTER -> "Daftar"; Mode.FORGOT -> "Kirim tautan"
                }
            )
        }

        when (mode) {
            Mode.LOGIN -> {
                TextButton({ reset(); mode = Mode.FORGOT }) { Text("Lupa kata sandi?", color = Brand) }
                TextButton({ reset(); mode = Mode.REGISTER }) { Text("Belum punya akun? Daftar", color = Brand) }
            }
            Mode.REGISTER -> TextButton({ reset(); mode = Mode.LOGIN }) { Text("Sudah punya akun? Masuk", color = Brand) }
            Mode.FORGOT -> TextButton({ reset(); mode = Mode.LOGIN }) { Text("Kembali masuk", color = Brand) }
        }

        if (mode == Mode.LOGIN) {
            Spacer(Modifier.weight(1f))
            Text("Demo: demo@hsk.id / 123456", color = Muted,
                modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
