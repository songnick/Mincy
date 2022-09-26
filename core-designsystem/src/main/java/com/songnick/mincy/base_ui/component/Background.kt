package com.songnick.mincy.base_ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.songnick.mincy.base_ui.LocalBackgroundTheme
import com.songnick.mincy.base_ui.LocalGradientColors
import com.songnick.mincy.base_ui.MincyTheme
import kotlin.math.tan

/*****
 * @author qfsong
 * Create Time: 2022/9/23
 **/
@Composable
fun MincyBackground(
    modifier: Modifier = Modifier,
    content: @Composable ()-> Unit
){
    val color = LocalBackgroundTheme.current.color
    val tonalElevation = LocalBackgroundTheme.current.tonalElevation
    Surface(
        color = if (color == Color.Unspecified){Color.Transparent} else color,
        tonalElevation = if(tonalElevation == Dp.Unspecified ){0.dp} else tonalElevation,
        modifier = modifier.fillMaxSize()
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

@Composable
fun MincyGradientBackground(
    modifier: Modifier = Modifier,
    topColor:Color = LocalGradientColors.current.primary,
    bottomColor:Color = LocalGradientColors.current.secondary,
    content:@Composable () -> Unit
){
    val curTopColor by rememberUpdatedState(topColor)
    val curBottomColor by rememberUpdatedState(bottomColor)
    MincyBackground(modifier = modifier) {
        Box(
            Modifier
                .fillMaxSize()
                .drawWithCache {
                    val offset = size.height * tan(
                        Math.toRadians(11.06).toFloat()
                    )
                    val start = Offset(size.width/2 + offset/2, 0f)
                    val end = Offset(size.width/2 - offset/2, size.height)

                    val topGradient = Brush.linearGradient(
                        0f to if (curTopColor == Color.Unspecified){
                            Color.Transparent
                        }else{
                            curTopColor
                        },
                        0.724f to Color.Transparent,
                        start = start,
                        end = end
                    )

                    val bottomGradient = Brush.linearGradient(
                        0.252f to Color.Transparent,
                        1f to if (bottomColor == Color.Unspecified){
                            Color.Transparent
                        }else{
                            curBottomColor
                        },
                        start = start,
                        end = end
                    )

                    onDrawBehind {
                        drawRect(topGradient)
                        drawRect(bottomGradient)
                    }
                }
        ) {
            content()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Composable
fun BackgroundDynamic() {
    MincyTheme(dynamicColor = true) {
        MincyBackground(Modifier.size(100.dp), content = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun GradientBackgroundDynamic() {
    MincyTheme(dynamicColor = true) {
        MincyGradientBackground(Modifier.size(100.dp), content = {})
    }
}