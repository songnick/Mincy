package com.songnick.mincy.feature.image_edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.songnick.mincy.core.data.model.Image
import com.songnick.mincy.core.design_system.MincyTheme
import com.songnick.mincy.core.design_system.component.MincyBackground
import com.songnick.mincy.core.design_system.component.MincySimpleTopAppBar
import com.songnick.mincy.feature.image_edit.compose.ImageEditImage

/*****
 * @author songnick
 * @mail qfsong108@gmail.com
 * Create Time: 2022/9/28
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageEditPage(
    modifier: Modifier = Modifier,
    picture: Image
) {
    MincyTheme {
        MincyBackground {
            Scaffold(
                modifier = Modifier,
                contentColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.background,
                topBar = {
                    MincySimpleTopAppBar(
                        navigationIcon = Icons.Rounded.ArrowBack,
                        actionIcon = Icons.Rounded.SaveAs
                    )

                },
                bottomBar = {
                    ImageEditBottomBar()
                }
            ) { paddingValues ->
                Box(modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.Red)){
                    ImageEditImage(picture = picture)
                }
            }
        }
    }
}


@Composable
fun ImageEditBottomBar(){
    Surface(color = MaterialTheme.colorScheme.surface) {
        ImageEditNavigationBar(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.safeDrawing
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
                )
        ) {
            ImageEditNavigationItem(
                onClick = { },
                icon = {Icon(painter = painterResource(id = R.drawable.bookmark_add), contentDescription = "")},
                label = { Text(text = "贴纸")}
            )
            ImageEditNavigationItem(
                onClick = { },
                icon = {Icon(painter = painterResource(id = R.drawable.bookmark_add), contentDescription = "")},
                label = { Text(text = "贴纸")}
            )
            ImageEditNavigationItem(
                onClick = { },
                icon = {Icon(painter = painterResource(id = R.drawable.brush), contentDescription = "")},
                label = { Text(text = "涂鸦")}
            )
        }
    }
}


@Composable
fun ImageEditNavigationBar(
        modifier: Modifier = Modifier,
        content: @Composable() RowScope.() -> Unit
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = MincyNavigationDefaults.NavigationContainerColor,
            contentColor = MincyNavigationDefaults.navigationContentColor(),
            tonalElevation = 0.dp,
            content = content
        )
    }

@Composable
fun RowScope.ImageEditNavigationItem(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = false,
        onClick = onClick,
        icon = icon,
        modifier = modifier.clip(RoundedCornerShape(24.dp)),
        enabled = true,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MincyNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = MincyNavigationDefaults.navigationContentColor(),
            selectedTextColor = MincyNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = MincyNavigationDefaults.navigationContentColor(),
            indicatorColor = MincyNavigationDefaults.navigationIndicatorColor()
        )
    )
}

/**
 * Mincy navigation default values.
 */
object MincyNavigationDefaults {
    val NavigationContainerColor = Color.Transparent

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}