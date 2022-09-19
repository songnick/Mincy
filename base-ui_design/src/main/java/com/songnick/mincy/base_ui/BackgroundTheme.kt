package com.songnick.mincy.base_ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/*****
 * @author qfsong
 * Create Time: 2022/9/6
 **/
@Immutable
data class BackgroundTheme (
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified
)

/**
 * A composition local for [BackgroundTheme].
 */
val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }