package com.songnick.mincy.media_choose

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.songnick.mincy.base_nav.MincyNavDestination

/*****
 * @author qfsong
 * Create Time: 2022/9/16
 **/
object MediaChooseNav:MincyNavDestination {
    override var route: String = "media_choose_route"
    override var destination: String = "media_choose_destination"
}

fun NavGraphBuilder.mediaChooseGraph(){
    composable(route = MediaChooseNav.route){
        ForMediaChooseRoute()
    }
}
