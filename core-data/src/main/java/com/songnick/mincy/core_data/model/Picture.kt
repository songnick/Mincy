package com.songnick.mincy.media_choose.model

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
data class Picture(
    override val path: String,
    override val name: String,
    val size:Int
): Media
