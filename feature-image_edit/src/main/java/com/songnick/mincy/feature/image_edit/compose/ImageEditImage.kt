package com.songnick.mincy.feature.image_edit.compose

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.songnick.mincy.core.data.model.Image

/*****
 * @author songnick
 * @mail qfsong108@gmail.com
 * Create Time: 2022/10/10
 **/
@Composable
fun ImageEditImage(picture: Image,needGpuImage:Boolean = false){
    if (needGpuImage){

    }else{
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picture.path)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}