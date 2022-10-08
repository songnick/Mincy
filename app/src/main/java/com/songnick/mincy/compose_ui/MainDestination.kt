package com.songnick.mincy.compose_ui

import com.songnick.mincy.core.design_system.Icon
import com.songnick.mincy.core.nav.MincyNavDestination


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