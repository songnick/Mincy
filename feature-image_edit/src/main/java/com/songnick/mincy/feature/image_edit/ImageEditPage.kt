package com.songnick.mincy.feature.image_edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.songnick.mincy.core.data.model.Image

/*****
 * @author qfsong
 * Create Time: 2022/9/28
 **/
@Composable
fun ImageEditPage(
    modifier: Modifier = Modifier,
    picture: Image
) {
    Box(modifier = modifier){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(picture.path)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().drawWithCache {
                onDrawBehind {  }

            }
        )
    }
}