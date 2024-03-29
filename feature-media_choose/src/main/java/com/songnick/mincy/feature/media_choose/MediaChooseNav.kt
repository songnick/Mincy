package com.songnick.mincy.feature.media_choose

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.songnick.mincy.core.nav.MincyNavDestination


/*****
 * @author qfsong
 * Create Time: 2022/9/16
 **/
object MediaChooseNav: MincyNavDestination {
    override var route: String = "media_choose_route"
    override var destination: String = "media_choose_destination"
}

fun NavGraphBuilder.mediaChooseGraph(){
    composable(route = MediaChooseNav.route){
        ForMediaChooseRoute()
    }
}
