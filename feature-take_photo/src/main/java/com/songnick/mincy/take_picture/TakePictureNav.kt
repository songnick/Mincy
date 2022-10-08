package com.songnick.mincy.take_picture

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.songnick.mincy.core.nav.MincyNavDestination

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
object TakePictureNav:MincyNavDestination {
    override var route: String = "take_picture_route"
    override var destination: String = "take_picture_destination"
}

fun NavGraphBuilder.takePictureGraph(){
    composable(route = TakePictureNav.route){
        TakePictureScreen()
    }
}