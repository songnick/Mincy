package com.songnick.mincy.core.design_system

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/*****
 * @author qfsong
 * Create Time: 2022/9/6
 **/
@Immutable
data class GradientColors (
    val primary: Color = Color.Unspecified,
    val secondary: Color = Color.Unspecified,
    val tertiary: Color = Color.Unspecified,
    val neutral: Color = Color.Unspecified
)


/**
 * A composition local for [GradientColors].
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColors() }
