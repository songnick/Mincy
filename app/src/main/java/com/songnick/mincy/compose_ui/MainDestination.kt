package com.songnick.mincy.compose_ui

import com.songnick.mincy.base_nav.MincyNavDestination
import com.songnick.mincy.base_ui.Icon


/*****
 * @author qfsong
 * Create Time: 2022/9/8
 **/
data class MainDestination (
    override var route: String,
    override var destination: String,
    var selectedIcon: Icon,
    var unSelectedIcon:Icon,
    var iconTextId:Int
) : MincyNavDestination