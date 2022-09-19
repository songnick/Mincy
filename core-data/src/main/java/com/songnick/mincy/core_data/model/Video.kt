package com.songnick.mincy.media_choose.model

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
data class Video(
    override val name:String,
    override val path:String,
    val duration:Long,
    val size:Int,
):Media
