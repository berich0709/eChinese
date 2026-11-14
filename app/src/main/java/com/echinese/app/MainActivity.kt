package com.echinese.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.echinese.core.EChineseTheme
import com.echinese.core.Lang
import com.echinese.core.LocalStrings
import com.echinese.core.stringsFor
import com.echinese.feature.auth.AuthFlow
import androidx.compose.runtime.CompositionLocalProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EChineseTheme {
                val ctx = LocalContext.current
                val store = remember { SessionStore(ctx) }
                var user by remember { mutableStateOf(store.user()) }
                var showGoal by remember { mutableStateOf(user != null && !store.goalSet()) }
                var lang by remember { mutableStateOf(Lang.valueOf(store.lang().uppercase())) }
                CompositionLocalProvider(LocalStrings provides stringsFor(lang)) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    if (user == null) AuthFlow(onLoggedIn = { u, isNew -> store.save(u); user = u; showGoal = isNew })
                    else if (showGoal) GoalScreen(user = user!!) { lvl, date, mins ->
                        store.saveGoal(lvl, date, mins); showGoal = false
                    }
                    else HomeScreen(user = user!!, onLogout = { store.clear(); user = null; showGoal = true },
                        onLang = { l -> lang = l; store.setLang(l.code) })
                }
                }
            }
        }
    }
}
