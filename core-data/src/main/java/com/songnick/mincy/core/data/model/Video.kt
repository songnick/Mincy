package com.songnick.mincy.core.data.model

import android.net.Uri

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
data class Video(
    override val name:String,
    override val path:String,
    override val uri: Uri,
    override val date: Long,
    val duration:Long,
    val size:Int,
):Media
