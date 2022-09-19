package com.songnick.mincy.compose_ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.core.os.trace
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.songnick.mincy.R
import com.songnick.mincy.base_nav.MincyNavDestination
import com.songnick.mincy.base_ui.Icon
import com.songnick.mincy.base_ui.MincyIcons
import com.songnick.mincy.media_choose.MediaChooseNav
import com.songnick.mincy.take_picture.TakePictureNav

/*****
 * @author qfsong
 * Create Time: 2022/9/9
 **/

@Composable
fun remeberMincyAppState(
    navHostController: NavHostController = rememberNavController()
):MincyAppState{
    
    return remember(navHostController) {
        MincyAppState(navHostController)
    }
}

@Stable
class MincyAppState(val navHostController: NavHostController){
    companion object{
        const val TAG = "MincyAppState"
    }


    val curDestination:NavDestination?
        @Composable get() = navHostController?.currentBackStackEntryAsState()?.value?.destination

    val destinations:List<MainDestination> = listOf(
        MainDestination(
            MediaChooseNav.route,
            MediaChooseNav.destination,
            Icon.DrawableIcon(MincyIcons.PhotoIconBorder),
            Icon.DrawableIcon(MincyIcons.PhotoIcon),
            R.string.navigation_picture,
        ),
        MainDestination(
            TakePictureNav.route,
            TakePictureNav.destination,
            Icon.DrawableIcon(MincyIcons.TakePhotoBorder),
            Icon.DrawableIcon(MincyIcons.TakePhoto),
            R.string.navigation_take_picture,
        ),
        MainDestination(
            "",
            "",
            Icon.DrawableIcon(MincyIcons.TakeVideoBorder),
            Icon.DrawableIcon(MincyIcons.TakeVideo),
            R.string.navigation_record_video,
        )
    )

    fun navigate(destination:MincyNavDestination, route:String? = null){
        Log.i(TAG, "navigate route: $route")
        if (destination is MainDestination && destination.route.isNotEmpty()){
            navHostController.navigate(route?:destination.route){
                popUpTo(navHostController.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true

                restoreState = true
            }
        }
    }
}