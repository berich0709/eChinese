package com.echinese.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.echinese.core.EChineseTheme
import com.echinese.feature.auth.AuthFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EChineseTheme {
                val ctx = LocalContext.current
                val store = remember { SessionStore(ctx) }
                var user by remember { mutableStateOf(store.user()) }
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (user == null) AuthFlow(onLoggedIn = { store.save(it); user = it })
                    else HomeScreen(user = user!!, onLogout = { store.clear(); user = null })
                }
            }
        }
    }
}
