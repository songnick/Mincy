package com.songnick.mincy.compose_ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.songnick.mincy.base_ui.component.MincyBackground
import com.songnick.mincy.compose_ui.nav.MincyNavHost
import com.songnick.mincy.core.design_system.Icon
import com.songnick.mincy.core.design_system.MincyTheme
import com.songnick.mincy.feature.media_choose.MediaChooseNav

/*****
 * @author qfsong
 * Create Time: 2022/9/7
 **/
const val TAG = "App"

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun App(
    appState: MincyAppState = remeberMincyAppState()
) {
    MincyTheme() {
        MincyBackground {
            Scaffold(
                modifier = Modifier.semantics { testTagsAsResourceId = true },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                bottomBar = {
                    MincyBottomBar(
                        destinationList = appState.destinations,
                        navigationToDestination = appState::navigate,
                        curDestination = appState.curDestination
                    )
                }
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    MincyNavHost(
                        navController = appState.navHostController,
                        onNavigationToDestination = appState::navigate,
                        onBackClick = { },
                        startDestination = MediaChooseNav.route,
                        modifier = Modifier
                            .padding(padding)
                            .consumedWindowInsets(padding)
                    )
                }
            }
        }
    }
}

@Composable
private fun MincyBottomBar(
    destinationList: List<MainDestination>,
    navigationToDestination: (MainDestination) -> Unit,
    curDestination: NavDestination?
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        MincyNavigationBar(
            modifier = Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                    )
                )
        ) {
            destinationList.forEach { destination ->
                val selected =
                    curDestination?.hierarchy?.any { it.route == destination.route } == true
                MincyNavigationItem(
                    selected = selected,
                    onClick = {
                        Log.i(TAG, " on  clicked ")
                        navigationToDestination(destination)
                    },
                    icon = {
                        when (val icon =
                            if (selected) destination.selectedIcon else destination.unSelectedIcon) {
                            is Icon.ImageVectorIcon -> Icon(
                                imageVector = icon.imageVector,
                                contentDescription = ""
                            )
                            is Icon.DrawableIcon -> Icon(
                                painter = painterResource(id = icon.drawableId),
                                contentDescription = ""
                            )
                            else -> {}
                        }
                    },
                    label = { Text(text = stringResource(id = destination.iconTextId)) })
            }
        }
    }
}

@Composable
fun MincyNavigationBar(
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
fun RowScope.MincyNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enable: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enable,
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
 * Now in Android navigation default values.
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