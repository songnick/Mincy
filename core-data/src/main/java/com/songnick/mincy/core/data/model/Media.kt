package com.songnick.mincy.core.data.model

import android.net.Uri

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
interface Media{
    val name:String
    val path:String
    val uri:Uri
    val date:Long
}
