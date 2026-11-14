package com.echinese.core

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * 设计 token：所有界面只引用 token，不写死颜色/圆角。
 * 换美工时整体替换 DefaultTokens 即可，页面无需改动。
 */
data class DesignTokens(
    val brand: Color,
    val brandSoft: Color,
    val bg: Color,
    val card: Color,
    val ink: Color,
    val muted: Color,
    val line: Color,
    val success: Color,
    val danger: Color,
    val radius: Int = 16,
    val buttonHeight: Int = 50
)

val DefaultTokens = DesignTokens(
    brand = Color(0xFF0B5D52),
    brandSoft = Color(0xFFE8F1EE),
    bg = Color(0xFFF7F9F8),
    card = Color(0xFFFFFFFF),
    ink = Color(0xFF152421),
    muted = Color(0xFF7C8A84),
    line = Color(0xFFE3EBE7),
    success = Color(0xFF2FA36B),
    danger = Color(0xFFE5484D)
)

val LocalTokens = staticCompositionLocalOf { DefaultTokens }
