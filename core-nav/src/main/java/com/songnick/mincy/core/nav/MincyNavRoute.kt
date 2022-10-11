package com.songnick.mincy.core.nav

import android.app.Activity
import android.content.Intent

/*****
 * @author songnick
 * @mail qfsong108@gmail.com
 * Create Time: 2022/10/10
 **/
object MincyNavRoute {

    const val IMAGE_EDIT_ROUTE = "com.songnick.mincy.feature.image_edit.Image"

    fun MincyNavRoute.start(){

    }

    fun route(action:String, activity: Activity){
        val intent = Intent()
        intent.action = action
        activity.startActivity(intent)
    }
}