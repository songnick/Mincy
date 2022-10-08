package com.songnick.mincy.core.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
@Parcelize
data class Image(
    override val path: String,
    override val name: String,
    override val uri: Uri,
    override val date: Long,
    val size:Int
): Media
