package com.echinese.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.echinese.core.EChineseTheme
import com.echinese.feature.auth.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EChineseTheme {
                Surface(color = MaterialTheme.colorScheme.background) { LoginScreen() }
            }
        }
    }
}
