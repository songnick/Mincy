package com.songnick.mincy.feature.media_choose.component

import android.text.format.Formatter
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import com.songnick.mincy.core.data.model.Video
import com.songnick.mincy.feature.media_choose.TimeUtil

/*****
 * @author qfsong
 * Create Time: 2022/9/27
 **/
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    video: Video,
    onClick:()->Unit
){
    Card(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable {
                    onClick.invoke()
                }
        ) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components {
                    add(VideoFrameDecoder.Factory())
                }
                .build()
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data((video.path))
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Spacer(modifier = Modifier
                .fillMaxSize()
                .background(Color(red = 0x00, green = 0x00, blue = 0x00, alpha = 0x4D)))
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .align(Alignment.TopEnd)
                    .padding(top = 5.dp, end = 5.dp)
            ) {
                Text(
                    modifier = Modifier.background(Color.Transparent),
                    text = TimeUtil.formattedTime(video.duration),
                    color = Color.White,
                    fontSize = 13.sp
                )
            }
        }


    }
}