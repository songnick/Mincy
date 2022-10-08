package com.songnick.mincy.core.design_system
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector


/*****
 * @author qfsong
 * Create Time: 2022/9/8
 **/
object MincyIcons {
    val PhotoIcon = R.drawable.photo_library
    val PhotoIconBorder = R.drawable.photo_library_selected
    val TakePhoto = R.drawable.add_a_photo
    val TakePhotoBorder = R.drawable.add_a_photo_selected
    val TakeVideo = R.drawable.photo_camera
    val TakeVideoBorder = R.drawable.photo_camera_selected
    val Search = Icons.Rounded.Search
    val AccountCircle = Icons.Outlined.AccountCircle
}

sealed class Icon{
    data class ImageVectorIcon(val imageVector: ImageVector):Icon()
    data class DrawableIcon(@DrawableRes val drawableId: Int):Icon()
}