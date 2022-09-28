package com.songnick.mincy.compose_ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.songnick.mincy.base_nav.MincyNavDestination
import com.songnick.mincy.feature.media_choose.MediaChooseNav
import com.songnick.mincy.feature.media_choose.mediaChooseGraph
import com.songnick.mincy.take_picture.takePictureGraph

/*****
 * @author qfsong
 * Create Time: 2022/9/9
 **/
@Composable
fun MincyNavHost(
    navController: NavHostController,
    onNavigationToDestination:(MincyNavDestination, String) -> Unit,
    onBackClick:()->Unit,
    modifier: Modifier = Modifier,
    startDestination:String = MediaChooseNav.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ){
        mediaChooseGraph()
        takePictureGraph()
    }

}