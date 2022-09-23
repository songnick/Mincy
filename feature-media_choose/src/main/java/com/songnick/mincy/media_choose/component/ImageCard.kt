package com.songnick.mincy.media_choose.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

/*****
 * @author qfsong
 * Create Time: 2022/9/23
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    path:String,
    selected:Boolean = false
){
    Card(
        modifier = modifier,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(path)
                .crossfade(true)
                .build(),
            contentDescription ="",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}