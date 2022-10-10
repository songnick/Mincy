package com.songnick.mincy.core.design_system.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/*****
 * @author qfsong
 * Create Time: 2022/10/9
 **/
@Composable
fun MincySimpleTopAppBar(
    title:String? = null,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector,
    actionIcon: ImageVector,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    navigationOnClick:() -> Unit = {},
    actionOnClick:() -> Unit = {}
){
    CenterAlignedTopAppBar(
        title = { title?.let { Text(text = it) } },
        navigationIcon = { IconButton(onClick = navigationOnClick) {
            Icon(imageVector = navigationIcon, contentDescription ="" )
        }
        },
        actions = {
            IconButton(onClick = actionOnClick) {
                Icon(imageVector = actionIcon, contentDescription = "")
            }
        },
        colors = colors,
        modifier = modifier
    )
}

@Composable
fun MincyTopAppBar(
    title:String? = null,
    modifier: Modifier = Modifier,
    navigation: @Composable ()->Unit,
    action: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    navigationOnClick:() -> Unit = {},
    actionOnClick:() -> Unit = {}
){
    CenterAlignedTopAppBar(
        title = { title?.let { Text(text = it) } },
        navigationIcon = navigation,
        actions = action,
        colors = colors,
        modifier = modifier
    )
}